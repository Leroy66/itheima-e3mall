# itheima-e3mall
益立方商城网站---黑马视频
  SSM+redis+dubbo+mycat+solr+ActiveMQ
  Maven+eclipse+git

5.2.	后台工程搭建分析
  Maven的常见打包方式：jar、war、pom
  Pom工程一般都是父工程，管理jar包的版本、maven插件的版本、统一的依赖管理。聚合工程。

  e3-parent：父工程，打包方式pom，管理jar包的版本号。项目中所有工程都应该继承父工程。
        |--e3-common：通用的工具类通用的pojo。打包方式jar
        |--e3-manager：服务层工程。聚合工程。Pom工程
                      |--e3-manager-dao：打包方式jar
                      |--e3-manager-pojo：打包方式jar
                      |--e3-manager-interface：打包方式jar
                      |--e3-manager-service：打包方式：jar
                      |--e3-manager-web：表现层工程。打包方式war
