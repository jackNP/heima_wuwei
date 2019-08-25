package com.itheima.web;

import com.itheima.domain.*;
import com.itheima.service.OrdersService;
import com.itheima.utils.BeanFactory;
import com.itheima.utils.PaymentUtil;
import com.itheima.utils.UUIDUtils;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.SimpleFormatter;

@WebServlet(urlPatterns = "/order")
public class OrderServlet extends BaseServlet {
    private OrdersService ordersService = BeanFactory.newInstance(OrdersService.class);

    /***
     * 支付回调的servlet方法
     */
    public String callback(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String p1_MerId = request.getParameter("p1_MerId");
        String r0_Cmd = request.getParameter("r0_Cmd");
        String r1_Code = request.getParameter("r1_Code");
        String r2_TrxId = request.getParameter("r2_TrxId");
        String r3_Amt = request.getParameter("r3_Amt");
        String r4_Cur = request.getParameter("r4_Cur");
        String r5_Pid = request.getParameter("r5_Pid");
        String r6_Order = request.getParameter("r6_Order");
        String r7_Uid = request.getParameter("r7_Uid");
        String r8_MP = request.getParameter("r8_MP");
        String r9_BType = request.getParameter("r9_BType");
        String rb_BankId = request.getParameter("rb_BankId");
        String ro_BankOrderId = request.getParameter("ro_BankOrderId");
        String rp_PayDate = request.getParameter("rp_PayDate");
        String rq_CardNo = request.getParameter("rq_CardNo");
        String ru_Trxtime = request.getParameter("ru_Trxtime");
        // 身份校验 --- 判断是不是支付公司通知你
        String hmac = request.getParameter("hmac");
        String keyValue = ResourceBundle.getBundle("merchantInfo").getString(
                "keyValue");

        // 自己对上面数据进行加密 --- 比较支付公司发过来hamc
        boolean isValid = PaymentUtil.verifyCallback(hmac, p1_MerId, r0_Cmd,
                r1_Code, r2_TrxId, r3_Amt, r4_Cur, r5_Pid, r6_Order, r7_Uid,
                r8_MP, r9_BType, keyValue);
        if (isValid) {
            // 响应数据有效
            if (r9_BType.equals("1")) {
                // 浏览器重定向
                System.out.println("111");
                request.setAttribute("msg", "您的订单号为:"+r6_Order+",金额为:"+r3_Amt+"已经支付成功,等待发货~~");
                request.setAttribute("oid",r6_Order);

            } else if (r9_BType.equals("2")) {
                // 服务器点对点 --- 支付公司通知你
                System.out.println("付款成功！222");
                // 修改订单状态 为已付款
                // 回复支付公司
                response.getWriter().print("success");
            }

            //修改订单状态
            //订单编号给你返回来了，直接拿来用
            ordersService.updateOrderState(r6_Order);

        } else {
            // 数据无效
            System.out.println("数据被篡改！");
        }

            //走完判断，转发到jsp
        request.getRequestDispatcher("success.jsp").forward(request,response);
        return "/jsp/msg.jsp";

    }

    /***
     * 支付的servlet方法
     */
    public void pay(HttpServletRequest request,HttpServletResponse respone) throws Exception{
        System.out.println("进入支付方法");
        //接受参数
        String address=request.getParameter("address");
        String name=request.getParameter("name");
        String telephone=request.getParameter("telephone");
        String oid=request.getParameter("oid");

        //业务层方法，修改订单信息,分别传入，名字，地址，电话，订单主键
        ordersService.updateOrder(name,address,telephone,oid);
//        //通过id获取order
//        OrderService s=new OrderService();
//        Order order = s.getOrderByOid(oid);
//
//        order.setAddress(address);
//        order.setName(name);
//        order.setTelephone(telephone);
//
//        //更新order
//        s.updateOrder(order);


        // 组织发送支付公司需要哪些数据
        String pd_FrpId = request.getParameter("pd_FrpId");
        String p0_Cmd = "Buy";
        String p1_MerId = ResourceBundle.getBundle("merchantInfo").getString("p1_MerId");
        String p2_Order = oid;
        String p3_Amt = "0.01";
        String p4_Cur = "CNY";
        String p5_Pid = "";
        String p6_Pcat = "";
        String p7_Pdesc = "";
        // 支付成功回调地址 ---- 第三方支付公司会访问、用户访问
        // 第三方支付可以访问网址
        String p8_Url = ResourceBundle.getBundle("merchantInfo").getString("responseURL");
        String p9_SAF = "";
        String pa_MP = "";
        String pr_NeedResponse = "1";
        // 加密hmac 需要密钥
        String keyValue = ResourceBundle.getBundle("merchantInfo").getString("keyValue");
        String hmac = PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt,
                p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc, p8_Url, p9_SAF, pa_MP,
                pd_FrpId, pr_NeedResponse, keyValue);


        //发送给第三方
        StringBuffer sb = new StringBuffer("https://www.yeepay.com/app-merchant-proxy/node?");
        sb.append("p0_Cmd=").append(p0_Cmd).append("&");
        sb.append("p1_MerId=").append(p1_MerId).append("&");
        sb.append("p2_Order=").append(p2_Order).append("&");
        sb.append("p3_Amt=").append(p3_Amt).append("&");
        sb.append("p4_Cur=").append(p4_Cur).append("&");
        sb.append("p5_Pid=").append(p5_Pid).append("&");
        sb.append("p6_Pcat=").append(p6_Pcat).append("&");
        sb.append("p7_Pdesc=").append(p7_Pdesc).append("&");
        sb.append("p8_Url=").append(p8_Url).append("&");
        sb.append("p9_SAF=").append(p9_SAF).append("&");
        sb.append("pa_MP=").append(pa_MP).append("&");
        sb.append("pd_FrpId=").append(pd_FrpId).append("&");
        sb.append("pr_NeedResponse=").append(pr_NeedResponse).append("&");
        sb.append("hmac=").append(hmac);
        //把字符串，响应回去
        Result result = new Result(Result.SUCCESS,"支付的情况",sb.toString());
        respone.getWriter().print(JSONObject.fromObject(result));

//        respone.sendRedirect(sb.toString());
//
//        return null;
    }
//支付结束
    /***
     *获取订单详情的方法
     * @param request
     * @param response
     * @throws Exception
     */
    public void info (HttpServletRequest request,HttpServletResponse response) throws Exception {
        //先进行登录验证
        User user = (User)request.getSession().getAttribute("user");
        if (user==null){
            Result re = new Result(Result.NOLOGIN,"没有登录");
            response.getWriter().print(JSONObject.fromObject(re));
            return;
        }
        //获取订单编号
        String oid = request.getParameter("oid");
        Orders orders =ordersService.info(oid);
        Result result = new Result(Result.SUCCESS,"查询订单详情成功",orders);
        response.getWriter().print(JSONObject.fromObject(result));


    }

    //分页看订单订单信息
    public void myOrderWithPage (HttpServletRequest request,HttpServletResponse response) throws Exception {
        //查看用户有没有登录
     //   System.out.println("测试进入");
        User user = (User)request.getSession().getAttribute("user");
        if (user==null){
            Result re = new Result(Result.NOLOGIN,"没有登录");
            response.getWriter().print(JSONObject.fromObject(re));
            return;
        }
        //如果登录了，就执行分页查看
        int currentPage= Integer.parseInt(request.getParameter("currentPage")) ;
        int pageSize=3;
        //得到分页对象
        PageBean<Orders> pb =ordersService.myOrderWithPage(currentPage,pageSize,user.getUid());
        Result result = new Result(Result.SUCCESS,"订单分页对象得到",pb);
        response.getWriter().print(JSONObject.fromObject(result));

    }




    public void submitOrder(HttpServletRequest request,HttpServletResponse response) throws Exception {
        // 添加订单的方法
        //从session 对象中去，用户对象
        User user = (User) request.getSession().getAttribute("user");
        if(user==null){
            //如果没有登录就提示登录
            Result result = new Result(Result.NOLOGIN,"没有登录账号");
            response.getWriter().print(JSONObject.fromObject(result));
            return;
        }else{
            //登录了，先检查购物车中有没有商品
            Cart cart = (Cart) request.getSession().getAttribute("cart");
            if(cart.getItemMap().size()==0){
                //购物项长度为0,表示没有商品
                Result result = new Result(Result.FAILS,"操作失败，没有上平");
                response.getWriter().print(JSONObject.fromObject(result));
                return;
            }else{
                //生成顶大，转化数据
                //订单类，的属性生成，订单ID，下单时间，总金额，付款没有，属于哪个用户
                Orders orders = new Orders();
                String uuid = UUIDUtils.getUUID();
                orders.setOid(uuid);
                //生成订单时间
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
                orders.setOrdertime(simpleDateFormat.format(new Date()));
                //设置总金额
                orders.setTotal(cart.getTotal());
                //未付款？
                orders.setState(Constr.ORDER_WEIFUKUAN);
                //属于哪个用户
                orders.setUid(user.getUid());

                //数据转化，购物想转换成，订单项，OrderItem
                /***
                 * 1.从购物车里面拿数据所有商品，存入collection
                 * 2.遍历商品，进行数据转化
                 */
                //购物车中的所有购物项，Collection集合，总结是都特么可以装，简称特能装
                Collection<CartItem> cartItems = cart.getItemMap();

                //创建集合，保存订单对象
                List<OrderItem> orderItemList = new ArrayList<>();
                //遍历。数据转化
                for (CartItem cartItem : cartItems) {
                    OrderItem orderItem = new OrderItem();
                    //设置订单主键
                    orderItem.setOid(uuid);
                    //设置商品主键
                    orderItem.setPid(cartItem.getProduct().getPid());
                    //设置数量
                    orderItem.setCount(cartItem.getCount());
                    //设置小计
                    orderItem.setSubTotal(cartItem.getSubTotal());

                    //存入订单数据
                    orderItemList.add(orderItem);
                }

//                为订单商品对象，转化问数据后，存入数据库
                ordersService.addOrders(orders,orderItemList);
//响应浏览器，生成的订单号，响应回去
                Result result = new Result(Result.SUCCESS,"订单创建成功",uuid);
                response.getWriter().print(result);
            }
        }

    }

}
