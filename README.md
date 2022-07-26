# 红岩 Android 2022年暑假考核

## 写在前面

　　不知不觉中，我已经在网校学习了一年了。从最开始的“Hello World”，到现在能够在指尖跳跃的 APP，从软件的使用者，到软件的开发者。一年里我上过的每一节课，做过每一次作业，~~掉过的每一根头发~~，熬过的每一个夜，换来的不仅仅是别人嘴中的一句“大佬”，还有自己肉眼可见的进步。

## 简介

　　**开眼App**，基本实现了接口的全部功能(~~业务代码要写吐了~~)，`Kotlin`100%，采用标准**MVVM架构**(没有用到仓库层),兼容`Android5.0`,界面UI还算可以(尽力美化了),适配**黑夜模式**,加入`Room`本地储存,加入了一些花里胡哨的的**动画**.

## 使用的第三方库

`GSYVideoPlaye `视频播放库

`PhoteView `图片查看库

`Glide` 图片查看库

`Retrofit+RxJava` 网络请求

## 效果图

<center class="half">    <img src="https://s2.loli.net/2022/07/26/Mmgjo4IdyZbcwUW.jpg" width="200"/><img src="https://s2.loli.net/2022/07/26/yx7NKbe54XtSvIF.jpg" width="200"/><img src="https://s2.loli.net/2022/07/26/J5MuIbDPEQcStie.jpg" width="200"/> </center>

<center class="half">    <img src="https://s2.loli.net/2022/07/26/jkzOXEGy3YlgDPT.jpg" width="200"/><img src="https://s2.loli.net/2022/07/26/pqBXf7yMPQkw8uR.jpg" width="200"/><img src="https://s2.loli.net/2022/07/26/hkHe8Oj2U5WCXG3.jpg" width="200"/> </center>

<center class="half">    <img src="https://s2.loli.net/2022/07/26/ShH5zrj1VcKUbmD.jpg" width="200"/><img src="https://s2.loli.net/2022/07/26/dIReAaZr3yhbovq.jpg" width="200"/><img src="https://s2.loli.net/2022/07/26/xXac6p5di7rNvzJ.jpg" width="200"/> </center>

<center class="half">    <img src="https://s2.loli.net/2022/07/26/D1OPneuJiQvMdK2.jpg" width="200"/><img src="https://s2.loli.net/2022/07/26/xJpGcjDHWhiymgL.jpg" width="200"/><img src="https://s2.loli.net/2022/07/26/ELtVlZx2OqbKYmo.jpg" width="200"/> </center>

<center class="half">    <img src="https://s2.loli.net/2022/07/26/vhuJjNk4tMxYDra.jpg" width="200"/><img src="https://s2.loli.net/2022/07/26/MCyUo9rkSGqLzvi.jpg" width="200"/><img src="https://s2.loli.net/2022/07/26/jTWqevBtl6hEI8m.jpg" width="200"/> </center>

主要功能有：启动图，日报，探索，排行，播放，搜索，观看历史，反馈

未实现功能：登录，评论，点赞（缺少API）

## 亮点

- 花里胡哨的的动画
- 黑夜模式
- 首页轮播图
- 上拉加载更多
- 无网适配
- 搜索历史,观看历史使用Room本地储存
- 侧滑删除
- ViewPager2包裹ViewPager2的滑动冲突

　**不足**: 没有用到高级的功能,从郭神那里cv了些封装好的base，大部分都是业务代码，缺少API，没有实现登录，评论，点赞功能．

## Commit记录

- **Day1,Day2** Initial commit
- **Day 3** 增加了开局动画(不知道为什么`SplashScreen`不能用),完善了日报的UI,添加日报的网络请求,**分析了郭神的积分商城**,准备明天把网络请求封装一下
- **Day 4** 更改了`ViewModel`,重新完善了网络请求,完成播放界面的部分UI
- **Day 5** 新增首页`banner`,涉及到滑动冲突, 规范`ViewModel`的写法,适配无网界面,完善相关视频的界面. 今天去网校写的,能和大家一起聊天,还有学长指导,比自己在宿舍闷头写好多了
- **Day 6** 完成首页上拉加载更多,修复了`banner`的一些问题(还没修复完),新增搜索界面
- **Day7** 完成搜索界面,加入`Room`本地储存
- **Day 8** 完成排行界面,完成部分搜索界面,修复搜索历史的bug,修复`ViewPager2`嵌套的部分bug
- **Day 9** 完成分类界面,完成分类详情界面,完善播放历史界面,完善消息界面
- **Day 10** 完成反馈界面,完成社区界面,完善首页`banner`的数据,修复`toolbar`标题消失的问题
- **Day 11** 今天没有**commit**,差分刷新失败,滑动冲突也没有解决
- **Day 12** 增加查看图片界面,完善播放历史的逻辑,增加`Room`删除和更新功能,cv了宇神的vp2指示器,发布**release**测试包
- **Day 13** 优化代码,增加注释,压缩安装包的体积,发布**Release**正式包

## 写在最后

  考核终于结束了,来网校的这一年中收获了很多,也成长了很多. 不管最终结果怎们样,我的大一生活也画上了圆满的句号.

  感谢红岩网校!!!🌹🌹🌹




