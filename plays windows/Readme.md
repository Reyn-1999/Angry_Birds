#### 代码逻辑

- Windows：
  - 为主界面添加四个主要板块：loading，chosen，play，victory
  - 循环执行，每次根据返回值判断接下来该加载运行的页面
- Plays：
  - 猪和小鸟的初始位置
  - 猪，小鸟，树杈的repaint()
  - 添加鼠标listener，来实现释放小鸟技能
  - 添加鼠标listener，来实现拖拽小鸟上树杈
- 具体内容请参考注释

#### 增加实现内容

- 在Plays类中增加了多种小鸟的类型
- 将每只鸟的数量限制从1拓展到数组
- 增加了多种小鸟的技能释放
- 增加关卡内容，具体指标由变量level设置

#### 增加新关卡

- windows中需要传输chosen板块选择关卡的变量
- windows需要将此变量传递给Plays的构造函数
- 具体需要改动的地方目前仅包括Plays类构造函数中的setBirdConf()和setPigConf()

#### 代码bug

- 目前无明显bug，只是红鸟偶尔会在投掷结束后停在屏幕中不消失，后续进行解决

#### 变量名问题

- bird类：isDrug
- button类，getWeight
- Pig类，exited (existed)
- Play类，distence
- Button类，isChonse