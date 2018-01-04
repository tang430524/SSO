# SSO
SSO单点登录
测试使用没有连接数据库 登录信息通过配置获取 tang   123   登录完成之后将token信息存在token中
采用MD5+AES加密将账号ID加密在token中 以此来判断重复登录
![](https://github.com/tang430524/SSO/blob/master/token.png)

当访问 http://127.0.0.1:9010/index.html 时会自动调整至登录页面
http://127.0.0.1:8010/login.html?reurl=http://127.0.0.1:9010/index.html
![](https://github.com/tang430524/SSO/blob/master/login.png)
登录完成跳转至先前记录的页面即 从哪里来到那里去 此时url会带有个token值 以此来验证
http://127.0.0.1:9010/index.html?token=f7Xyd7sUKjB2Pmm9XjIkSRR6HijKnkkYwKJlNcb1oUwaXnwFICx07OBV4NYN7mM1
![](https://github.com/tang430524/SSO/blob/master/hello.png)
登录完成将登陆信息显示出来

![](https://github.com/tang430524/SSO/blob/master/hi.png)
