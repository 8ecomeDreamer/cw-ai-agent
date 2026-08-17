
## 踩坑
1.调用mcp:npm与电脑的nvm冲突了，然后全局安装了 @amap/amap-maps-mcp-server 和 zod 两个模块可以解决。拉了springaimcp依赖之后，然后springai就会去找到mcp-servers.json执行对应命令，npx -y 其实就是下载命令，下载mcp对应的包。然后我遇到的是npx与nvm冲突了，正常解决办法应该是修改npx指向或者全局安装包，更好的操作应该是修改command指令解决指向问题。