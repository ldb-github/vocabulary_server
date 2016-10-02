# vocabulary_server
This is the server of vocabulary app. Please see the vocabulary_android repository if you want to known about the android app.

---项目过程---

2016.09.25之前
<p>完成验证码获取、注册、登录功能，词汇分类、词汇列表android视图层及服务层的模拟数据.</p>

2016.09.25
<p>设计及创建category, vocabulary, category_vocabulary, vocabulaty_communication表结构.</p>
<p>准备开发category和vocabulary相关的service层和dao层.</p>

2016.09.26
<p>完成获取词汇类别列表的dao层和service层编码.为图片上传做技术准备.</p>

2016.09.29
<p>实现图片上传处理功能.</p>
<p>参考：<a href="http://www.cnblogs.com/xdp-gacl/p/4200090.html">JavaWeb学习总结(五十)——文件上传和下载</a></p>

2016.09.30
<p>1、对数据库主键生成方式作了调整，原来是通过Oracle的Sequence进行自增处理；现在改为：使用独立的参数表记录主键，不同表的主键使用不同参数控制。目前只是调整了Category表的主键，之前已经完成的功能，比如Account，后续继续调整为此模式.</p>
<p>2、完成了新增词汇类别的功能。</p>
<p>3、对获取图片的代码作了调整，从servlet层移到service层。</p>

2016.10.02
<p>完成新增词汇、获取词汇列表功能.</p>