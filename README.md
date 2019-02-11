使用**IDEA创建maven项目**，勾选从模板原型构建，这里选择maven-archetype-quickstart类型，这种类型的maven项目以jar包的方式提供对外统一的输出，这是最快速的一个构建Spring Boot研发项目的模板。在没有Spring Boot的开发框架时，更多的选择maven-archetype-webapp，这种方式创建的是以war包方式部署在Tomcat或JBoss这种J2EE的容器。详见[Maven官方文档](http://maven.apache.org/guides/introduction/introduction-to-archetypes.html)

 创建成功后，需要给**目录**指定其为什么目录，选定项目文件夹，右键选择Mark Directory as，然后选择目录类型。还要在main目录下创建resources目录并指定类型。main目录下的java目录下放置java源代码，resources目录下放置spring、spring boot等的资源配置文件。

从零**集成项目**：进入[Spring官方文档](https://spring.io/guides/gs/rest-service/)，查看Build with Maven目录下pom.xml文件，从其中复制spring-boot-starter-parent依赖到项目中的pom.xml文件中，这样当前的项目就是Spring Boot项目。并在项目pom文件<dependencies>标签内引入spring-boot-starter-parent下的spring-boot-starter-web和spring-boot-starter-test项目。

在APP class上加@EnableAutoConfiguration注解
将APP的**启动类**当成可以支持自动化配置的bean，并且能够开启整个工程类基于springboot的自动化的配置，然后用下面这行代码启动Spring Boot。

```
SpringApplication.run(App.class,args);
```

要实现Spring MVC的***controller***功能，则引入注解@RestController或@Controller

 Spring Boot 对应的**配置化操作**，只需要在resources目录下创建默认配置文件——application.yml或application.properties，在其中进行配置。

**集成Mybatis**：进入pom文件，确定使用的数据库，使用mysql，则引入mysql-connector-java包；确定使用什么连接池来管理mysql的链接，这里使用阿里巴巴的druid连接池。然后将spring boot对mybatis的支持引入，这里使用mybatis-spring-boot-starter包；在配置文件类导入mybatis需要的一些配置，用来启动一个带mybatis数据库访问的一个spring boot工程，例：

```yml
mybatis:
  mapper-locations: classpath:mapping/*.xml

spring:
  datasource:
    name: miaohsa
    url: jdbc:mysql://localhost:3306/miaosha?useSSL=false&useUnicode=true&characterEncoding=utf8&serverTimezone=UTC
    username: root
    password: 123456
    #使用druid数据源
    type: com.alibaba.druid.pool.DruidDataSource
    driver-class-name: com.mysql.jdbc.Driver
```

然后使用**mybatis的自动生成工具**，用来生成对应数据库文件的映射。

在pom中引入mybatis自动生成的插件，例：

```xml
<plugin>
  <groupId>org.mybatis.generator</groupId>
  <artifactId>mybatis-generator-maven-plugin</artifactId>
  <version>1.3.5</version>
  <dependencies>
    <dependency>
      <groupId>org.mybatis.generator</groupId>
      <artifactId>mybatis-generator-core</artifactId>
      <version>1.3.5</version>
    </dependency>
    <dependency>
      <groupId>mysql</groupId>
      <artifactId>mysql-connector-java</artifactId>
      <version>5.1.47</version>
    </dependency>
  </dependencies>
  <executions>
    <execution>
      <id>mybatis generator</id>
      <phase>package</phase>
      <goals>
        <goal>generate</goal>
      </goals>
    </execution>
  </executions>
  <configuration>
    <!--允许移动生成的文件-->
    <verbose>true</verbose>
    <!--不允许自动覆盖文件-->
    <overwrite>false</overwrite>
    <configurationFile>
      src/main/resources/mybatis-generator.xml
    </configurationFile>
  </configuration>
</plugin>
```

其中mybatis-generator.xml文件在[官网](http://www.mybatis.org/generator/configreference/xmlconfig.html)中有，可粘贴过来，删改使用，例：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE generatorConfiguration
        PUBLIC "-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN"
        "http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd">

<generatorConfiguration>

    <context id="DB2Tables" targetRuntime="MyBatis3">
        <!--数据库链接地址账号密码-->
        <jdbcConnection driverClass="com.mysql.jdbc.Driver"
                        connectionURL="jdbc:mysql://localhost:3306/miaosha?useUnicode=true&amp;characterEncoding=utf8&amp;serverTimezone=UTC"
                        userId="root"
                        password="123456">
        </jdbcConnection>

        <!--生成dataobject类的存放位置-->
        <javaModelGenerator targetPackage="com.miaosha.dataobject" targetProject="src/main/java">
            <property name="enableSubPackages" value="true" />
            <property name="trimStrings" value="true" />
        </javaModelGenerator>

        <!--生成映射文件存放位置-->
        <sqlMapGenerator targetPackage="mapping"  targetProject="src/main/resources">
            <property name="enableSubPackages" value="true" />
        </sqlMapGenerator>

        <!--生成Dao类的存放位置-->
        <!-- 客户端代码，生成易于使用的针对Model对象和XML配置文件的代码
              type="ANNOTATIONDMAPPER"，生成Java Model和基于注解的Mapper 对象
              type="MIXEDMAPPER",生成基于注解的Java Model和相应的Mapper对象
              type="XMLMAPPER",生成SQLMap XML 文件和独立的Mapper接口
         -->
        <javaClientGenerator type="XMLMAPPER" targetPackage="com.miaosha.dao"  targetProject="src/main/java">
            <property name="enableSubPackages" value="true" />
        </javaClientGenerator>

        <!--生成对应表和类名-->
        <table tableName="user_info" domainObjectName="UserDO" enableCountByExample="false"
               enableUpdateByExample="false" enableSelectByExample="false"
               enableDeleteByExample="false" selectByExampleQueryId="false"></table>
        <table tableName="user_password" domainObjectName="UserPasswordDO" enableCountByExample="false"
               enableUpdateByExample="false" enableSelectByExample="false"
               enableDeleteByExample="false" selectByExampleQueryId="false"></table>

    </context>
</generatorConfiguration>
```

在Run/Debug Configuration下新建一个Maven的配置，例：

![mybatis generator](img/mybatis generator.jpg)

将@EnableAutoConfiguration注解改为@SpringBootApplication，其等价于以默认属性使用@Configuration ， @EnableAutoConfiguration 和@ComponentScan 。最终启动类注解例：

```java
@SpringBootApplication(scanBasePackages = {"com.miaosha"})
@RestController
@MapperScan("com.miaosha.dao")
```



使用**SpringMVC方式进行开发**，项目结构

![项目结构](img/秒杀项目结构.png)

其中***dao层***与***dataobject层***由mybatis自动生成工具生成，dataobject层（负责数据存储到service层的传输）下的类对应数据库对象模型，其中的字段与数据库一一映射，dao层下的类定义对数据库进行交互的方法，在resources的mapping目录下有自动生成dao层对应的配置文件，一起实现对数据库的操作。

在***service层***下model目录下创建对象模型（不可以把数据库的映射简单透传返回给想要service的服务，这个model对应Spring MVC中业务逻辑交互的模型），然后在数据库中创建对应的表，在service层下创建对应对象的service接口，接口中定义需要的方法（方法的返回类型大部分为model对象），在service层下impl目录下去实现。添加方法：在mapping目录下对应配置文件中添加相应的SQL语句，在dao目录下的对应接口中建立映射(添加方法)。

**注：**若数据库表中设有自增id，需在mapping目录下配置文件中的insert标签中添加属性useGeneratedKeys="true" 和keyProperty="id"，指定自增id

在serviceImpl中定义数据库对象dataobject与模型对象model相互转换的方法，在controller中定义模型对象model与视图对象viewobject相互转换的方法，这里使用到了***org.springframework.beans.BeanUtils***方法（作用为将一个Bean对象中的数据封装到另一个属性结构相似的Bean对象中，有些属性可能由于类型原因无法封装，需要手动添加）。

serviceImpl类与controller类中分别需要添加***@Service注解和@Controller注解***

***response层***定义了统一返回类型，有一个通用对象，最终返回前端的对象为该通用对象

***error层***有一个common error接口，一个**Enum枚举类**实现该接口，该枚举类通过构造方法构造一个实现common error接口的enum类型的子类，一个继承Exception且实现common error方法的类，其内强关联一个对应的common error（即enum类），且实现其对应构造函数以方便使用。该方式对应设计模式——**包装器业务异常类实现**。

在***controller层***下viewobject目录下创建可供UI使用的对象，其中的字段为可以给用户查看的字段。在controller层下创建对应对象的controller类，在其中调用其service接口中的方法来实现相应逻辑。

controller层下还有一个基类BaseController，其定义了controller类下接口上的@RequestMapping注解中的属性*consumes*（指定处理请求的提交内容类型）的默认值（GET请求不需要）；并通过定义exceptionhandler解决未被controller层吸收的exception（为业务逻辑处理上的问题或业务逻辑错误而并非服务端不能处理的错误），例：

```java
@ExceptionHandler(Exception.class)//需要指明收到什么样的exception之后才会进入它的处理环节，此处定义为根类
@ResponseStatus(HttpStatus.OK)//捕获到controller抛出的exception，并返回HttpStatus.OK,即status=200
@ResponseBody //handler exception使用这种方式（Object会寻找本地页面文件）仅仅只能返回页面路径，无法处理viewobject类对应的@ResponseBody形式，加上@ResponseBody注解即可解决
public Object handlerException(HttpServletRequest request, Exception ex){
    //获取异常并处理
}
```

其他所有controller类都要继承基类BaseController

在UserController中，用户登录接口中将登陆凭证加入到用户登录成功的***session***中

```java
@Autowired
private HttpServletRequest httpServletRequest;
//通过bean的方式注入进来，代表这个HttpServletRequest是单例模式
//单例模式怎么可以支持一个request支持多个用户的并发访问？
//bean包装的HttpServletRequest，本质是一个proxy，它内部拥有ThreadLocal方式的map，去让用户在每个线程当中去处理它自己对应的request，
//并且有ThreadLocal清除机制，可以放心使用，且这个HttpServletRequest对应当前用户的http请求
```

```java
//将登陆凭证加入到用户登录成功的session中，分布式中用token
this.httpServletRequest.getSession().setAttribute("IS_LOGIN",true);
this.httpServletRequest.getSession().setAttribute("LOGIN_USER",userVO);
```

**登录密码加密：**

因为jdk自带的MD5实现的方式只支持16位MD5，更改加密方法，例：

```java
//将密码加密
public String EncodeByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
    //确定计算方法
    MessageDigest md5 = MessageDigest.getInstance("MD5");
    BASE64Encoder base64Encoder = new BASE64Encoder();
    //加密字符串
    String newstr = base64Encoder.encode(md5.digest(str.getBytes("utf-8")));
    return newstr;
}
```

***validator层***下为*格式化校验规则*，在pom文件中引入Apache Commons Lang包，使用了其StringUtils工具类；还引入了hibernate-validator包来进行校验。其下建立一个校验结果对象，对象中还创建一个获取错误信息的方法以便使用；并创建一个实现InitializingBean类的一个校验实现类，将hibernate validator 通过工厂校验的方式使其实例化，然后实现校验方法并返回校验结果对象。另外对字段有什么限制可在model层下对象模型的字段上添加注解如@NotBlank、@NotNull、@Max、@Min等。

**跨域解决：**

在controller类上加上如下注解：

```java
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")  //实现跨域
```

DEFAULT_ALLOW_CREDENTIALS = true：
需配合前端设置，xhrFields授信后



使得跨域session共享
前端**ajax**请求添加设置：xhrFields:{withCredentials:true}

DEFAULT_ALLOWED_HEADERS = *：允许跨域传输所有的header参数，将用于使用token放入header域做session共享的跨域请求