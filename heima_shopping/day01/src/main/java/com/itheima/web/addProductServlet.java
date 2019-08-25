package com.itheima.web;

import com.itheima.domain.Product;
import com.itheima.service.ProductService;
import com.itheima.utils.BeanFactory;
import com.itheima.utils.UUIDUtils;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.*;

@WebServlet(urlPatterns = "/addProduct")
public class addProductServlet extends HttpServlet {
    private ProductService productService = BeanFactory.newInstance(ProductService.class);
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String,String[]> map =getMap(request);
        Product product = new Product();
        try {
            BeanUtils.populate(product,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        //设置主键
        product.setPid(UUIDUtils.getUUID());
        //设置发布日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date());
        product.setPdate(date);
        //设置上下架
        product.setPflag( Constr.PFLAG );

        productService.addProduct(product);
        response.setContentType("text/html;charset=utf-8");
        Result re = new Result(Result.SUCCESS,"添加成功");
        response.getWriter().print(JSONObject.fromObject(re));
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    private Map<String,String[]> getMap(HttpServletRequest request){
        Map<String,String[]> map = new HashMap<>();
        try {
            //创建磁盘工厂对象
            DiskFileItemFactory itemFactory = new DiskFileItemFactory();
            //创建Servlet的上传解析对象,构造方法中,传递磁盘工厂对象
            ServletFileUpload fileUpload = new ServletFileUpload(itemFactory);
            /*
             * fileUpload调用方法 parseRequest,解析request对象
             * 页面可能提交很多内容 文本框,文件,菜单,复选框 是为FileItem对象
             * 返回集合,存储的文件项对象
             */
            List<FileItem> list =  fileUpload.parseRequest(request);
            for(FileItem item : list){
                //遍历集合,取出所有文件项对象
                //isFormField()文件项判断,返回true,是普通项
                //方法返回false,是附件项
                if(item.isFormField()){
                    //获取表单标签的name属性值  name="username"
                    String name = item.getFieldName();
                    //获取表单填写值getString("编码表")
                    String value = item.getString("utf-8");
                    //   System.out.println(name+"=="+value);

                    //是普通项，添加进map,准备封装
                    map.put(name,new String[]{value});

                }else {
                    //处理附件项,上传文件
                    //获取上传的文件名   Lighthouse.jpg
                    String filename = item.getName();
                    //修改上传后的文件,split，参数用正则表达，取前后2段，要1下标段的值。
                    String houzhui = filename.split("\\.")[1];
                    filename =UUID.randomUUID().toString().replace("-","")+"."+houzhui;

                    //处理上传路径,日期作为目录名
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String date = sdf.format(new Date());

                    // 读取配置文件,获得上传路径
                    ResourceBundle resourceBundle = ResourceBundle.getBundle("upload");
                    String uploadDir= resourceBundle.getString("uploadDir");
                    //创建出来 File.mkdirs()
                    //上传目录,和日期,组合成一个File对象
                    File uploadFile= new File(uploadDir,date);
                    //exists判断路径是否存在,存在返回true
                    if(!uploadFile.exists())
                        uploadFile.mkdirs();
                    //带日期的上传路径uploadFile,文件名,组成一个File对象
                    File uploadFileName = new File(uploadFile,filename);
                    System.out.println(uploadFileName);
                    //uploadFileName=E:\2018\黑马331\javaweb\商城项目\第一天\web\resources\products/2018-11-2/文件名
                    //获取上传的附件数据,字节输入流,读取上传的文件
                    InputStream inputStream = item.getInputStream();
                    FileOutputStream fos = new FileOutputStream(uploadFileName);
                    IOUtils.copy(inputStream,fos);
                    //图片上传完成，添加文件路径的属性
                    String lujing="\\resources\\products\\"+date+"\\"+filename;
                    //测试字符串路径是否正确
                    System.err.println(lujing);
                    map.put("pimage",new String[]{lujing});
                    IOUtils.closeQuietly(fos);
                    IOUtils.closeQuietly(inputStream);
                    //删掉tomcat 的临时文件，防止文件积累过大
                    item.delete();
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return map;
    }
}
