module.exports = {
    devServer: {
        overlay: {
            // 让浏览器 overlay 同时显示警告和错误
            warnings: true,
            errors: true
        },
        host: 'localhost',
        port: 8080, // 端口号
        https: false, // https:{type:Boolean}
        open: true, // 配置自动启动浏览器
        hotOnly: true, // 热更新
        proxy: {
            // 配置多个跨域
            '/api': {
                target: 'http://127.0.0.1:8081',//跨域接口的地址
                changeOrigin: true, //将其设置为true表示允许跨域
            }
        },
        headers: {
            'Access-Control-Allow-Origin': '*',
        }
    }
}