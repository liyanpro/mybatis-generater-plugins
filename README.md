### mybatis-generater-plugins 自动生成sql文件

###项目中引用，添加config.xml配置文件

        <?xml version="1.0" encoding="UTF-8" ?>
        <!DOCTYPE generatorConfiguration PUBLIC "-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN" "http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd" >
        <generatorConfiguration>
        	<classPathEntry
        			location="/Users/liyan/maven-3.2.5/maven-dependcies/mysql/mysql-connector-java/5.1.33/mysql-connector-java-5.1.33.jar"/>
        	<context id="context1" targetRuntime="MyBatis3">
        		<plugin type="cloud.liyan.mybatis.plugins.MySQLLimitPlugin"></plugin>
        
        		<jdbcConnection driverClass="com.mysql.jdbc.Driver"
        						connectionURL="jdbc:mysql://localhost:3306/liyan_database?useUnicode=true&amp;characterEncoding=UTF-8"
        						userId="liyan" password="****"/>
        		<!-- model路径和项目中java代码路径 -->				
        		<javaModelGenerator targetPackage="../model"
        							targetProject="../src/main/java/"/>
        		<!-- 要生成的mapper.xml文件路径和项目中mapper接口路径 -->						
        		<sqlMapGenerator targetPackage="mappers"
        						 targetProject="../src/main/resources"/>
        		<javaClientGenerator targetPackage="../dao"
        							 targetProject="../src/main/java/"
        							 type="XMLMAPPER"/>
        
        		<table schema="dataBase" tableName="tableName"/>
        
        	</context>
        </generatorConfiguration>
        
        <!--eclipse中执行 -->
        <!-- 选择pom.xml文件，击右键先择Run AS——>Maven Build… ——>在Goals框中输入：mybatis-generator:generate -->
        <!--idea中执行 -->
        <!-- edit configrations，添加 Maven，在Command-line 中输入：mybatis-generator:generate -e -->