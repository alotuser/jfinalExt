# jfinalExt
                                        JFinal mass assignment
                                        
                                        
                                        
 /**
	 * save 与 update 的业务逻辑在实际应用中也应该放在 serivce 之中，
	 * 并要对数据进正确性进行验证，在此仅为了偷懒
	 */
	@Before(BlogValidator.class)
	public void save() {
		getBean(Blog.class).save();//此方法不安全，请参考 update方法
		redirect("/blog");
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
  
  
  
  注解方式启动项目
  
  
  /**
	 * 配置路由
	 */
	public void configRoute(Routes me) {
		
		//me.add("/", IndexController.class, "/index");	// 第三个参数为该Controller的视图存放路径
		//me.add("/blog", BlogController.class);			// 第三个参数省略时默认与第一个参数值相同，在此即为 "/blog"
	
		//-----------alotuser--------------//
		JRoutes jme=new JRoutes(me);
		
		jme.adds("com.demo");//@方式一， 加载指定包下控制类
		//me.scan("", JType.SECOND, "com.demo");//方式二，区分级别
	}
	/**
	 * 配置插件
	 */
	public void configPlugin(Plugins me) {
		// 配置 druid 数据库连接池插件
		DruidPlugin druidPlugin = new DruidPlugin(PropKit.get("jdbcUrl"), PropKit.get("user"), PropKit.get("password").trim());
		me.add(druidPlugin);
		//---------alotuser-配置ActiveRecord插件----------------------------------------------//
		JActiveRecordPlugin arp = new JActiveRecordPlugin(druidPlugin);
		arp.setDialect(new OracleDialect());//设置数据库方言
		arp.setContainerFactory(new CaseInsensitiveContainerFactory());//忽略大小写
		arp.setShowSql(true);
		// 所有映射在 MappingKit 中自动化搞定
		
		//_MappingKit.mapping(arp);
		//------------alotuser-----------------//
		arp.addMapping("com.demo");//@加载指定包下实体类
		me.add(arp);
	}
  
  
  
