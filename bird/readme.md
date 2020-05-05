流程：
Plays：
初始化redbird、yellowbird位置
如果bird.isCollide(pig)，pig消失
如果birdX>200（弹弓的位置）且没被drug且被选择，则bird在飞
如果bird被drug，则bird随鼠标移动
点击鼠标：变身->选中鸟
长按鼠标：如果bird被choose，则bird被drug
放开鼠标：如果bird被drug，则设置Vx、Vy，取消drug，尝试bird fly

红鸟：影响范围为r=50
黄鸟：在空中“俯冲时”点击变2倍速
蓝鸟：在空中点击分裂为3个鸟
黑鸟：在空中点击爆炸，影响范围为r=150
白鸟：在空中点击下蛋，蛋垂直下落，碰到猪或地面则爆炸，影响范围为r=100，鸟继续飞翔，影响范围r=50

问题：
1.play类中选红鸟后选黄鸟，两个位置将重叠
2.play和bird类中distance拼写成了distence
3.play和button类中isChosen拼成了ischonse

改动：
增加Bluebird、Blackbird、Whitebird类，及其image
修改Play：新鸟的定义、新鸟位置的初始化、更新新鸟的图像、鼠标点击时的逻辑
（可通过在原始代码中搜索redbird关键词找到所有需更改的地方）