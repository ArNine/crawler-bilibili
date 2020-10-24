# crawler-bilibili-comment
爬取B站视频评论及评论者基本信息


爬取b站评论，以下网址皆为举例



#### 1.首先获得某视频的aid

aid是每个视频对应评论api接口的id

通过jsoup.connect获得静态页面，用正则取出aid，



#### 2.随后访问

https://api.bilibili.com/x/v2/reply?callback=jQuery172021564483459551953_1603503739445&jsonp=jsonp&pn=1&type=1&oid=585103471&sort=2&_=1603503874683

注意，这里要去掉callback=jQuery172021564483459551953_1603503739445&jsonp=jsonp才能正常获取json格式的评论数据

也就是访问https://api.bilibili.com/x/v2/reply?pn=1&type=1&oid=585103471&sort=2&_=1603500190026



##### url含义

pn：代表评论第几页

oid：即aid

sort：为0的时候代表按时间排序，大于0代表按热度排序

type：目前还不知道。。。不过好像只能取1

##### json含义

```json
{"num":1,"size":20,"count":757,"acount":2108}
```

num：代表第几页

size：代表一页评论数量

count：代表评论数，不包括楼中楼

acount：代表评论总数，包括楼中楼

其中在json最开始有rcount变量代表楼中楼评论数量（不算楼主）

#### 3.获取楼中楼

在json格式的评论数据中，有这样一些数据

rpid：代表本楼的id

root：好像和rpid一样，是本楼的id

parent_str：代表楼中评论所在楼的id



获取到rpid后，访问https://api.bilibili.com/x/v2/reply/reply?callback=jQuery172011701350135032818_1603501976897&jsonp=jsonp&pn=1&type=1&oid=585103471&ps=10&root=3629725429&_=160350308426，同样的这里也要去掉callback=jQuery172011701350135032818_1603501976897&jsonp=jsonp

https://api.bilibili.com/x/v2/reply/reply?pn=1&type=1&oid=585103471&ps=10&root=3629725429&_=160350308426

pn：代表楼中楼的第几页

oid：视频aid

ps：代表获取几条评论

root：就是本楼的rpid



