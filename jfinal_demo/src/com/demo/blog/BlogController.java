package com.demo.blog;

import com.jfinal.aop.Before;
import com.jfinal.aop.Inject;
import com.jfinaljext.aop.JController;
import com.jfinaljext.core.Controller;
import com.demo.common.model.Blog;

/**
 * 本 demo 仅表达最为粗浅的 jfinal 用法，更为有价值的实用的企业级用法
 * 详见 JFinal 俱乐部: http://jfinal.com/club
 * 
 * BlogController
 * 所有 sql 与业务逻辑写在 Model 或 Service 中，不要写在 Controller 中，养成好习惯，有利于大型项目的开发与维护
 */
@JController("/blog")
@Before(BlogInterceptor.class)
public class BlogController extends Controller {
	
	@Inject
	BlogService service;
	
	public void index() {
		setAttr("blogPage", service.paginate(getParaToInt(0, 1), 10));
		render("blog.html");
	}
	
	public void add() {
	}
	
	/**
	 * save 与 update 的业务逻辑在实际应用中也应该放在 serivce 之中，
	 * 并要对数据进正确性进行验证，在此仅为了偷懒
	 */
	@Before(BlogValidator.class)
	public void save() {
		getBean(Blog.class).save();//此方法不安全，请参考 update方法
		redirect("/blog");
	}
	
	public void edit() {
		setAttr("blog", service.findById(getParaToInt()));
	}
	
	/**
	 * save 与 update 的业务逻辑在实际应用中也应该放在 serivce 之中，
	 * 并要对数据进正确性进行验证，在此仅为了偷懒
	 */
	@Before(BlogValidator.class)
	public void update() {
		//注： 在Blog表新增money字段类型为number并且可为空
		//原实例方法
		//getBean(Blog.class).update();
		//redirect("/blog");
		
		Blog blog=null;
		
		//一,新方法 Bean方式
		blog = getBean(Blog.class);// 原方法 不安全
		//// 方案一：保留法
		blog=getBean(Blog.class,new String[]{"id","title","content"});
		//// 方案二：排除法
		// blog=getBean(Blog.class, ModelFilterType.REFUSE, "Money");
		//// 方案三：过滤器
		//String acceptStrs = ",id,title,content,", //只接受字段
		//			refuseStrs = ",content,money,";//拒绝接受字段
		// blog=getBean(Blog.class, new ModelFilter(){
		// @Override
		// public boolean accept(String paraName) {
		// return acceptStrs.contains(","+paraName+",");//可以使用正则表达式
		// }
		// @Override
		// public boolean refuse(String paraName) {
		// return refuseStrs.contains(","+paraName+",");//可以使用正则表达式
		// }
		// });
		//二,新方法 Model方式
		//blog = getModel(Blog.class);// 原方法 不安全
		//// 方案一：保留法
		// blog=getModel(Blog.class,new String[]{"id","title","content"});
		//// 方案二：排除法
		// blog=getModel(Blog.class, ModelFilterType.REFUSE, "Money");
		//// 方案三：过滤器
		//String acceptStrs = ",id,title,content,", //只接受字段
		//			refuseStrs = ",content,money,";//拒绝接受字段
		// blog=getModel(Blog.class, new ModelFilter(){
		// @Override
		// public boolean accept(String paraName) {
		// return acceptStrs.contains(","+paraName+",");//可以使用正则表达式
		// }
		// @Override
		// public boolean refuse(String paraName) {
		// return refuseStrs.contains(","+paraName+",");//可以使用正则表达式
		// }
		// });

		blog.update();

		redirect("/blog");
		
	}
	
	public void delete() {
		service.deleteById(getParaToInt());
		redirect("/blog");
	}
}


