module.exports = {
    title: 'flash-waimai',
    description: '使用flash-waimai快速构建外卖系统',
    base: '/flash-waimai/',
    head: [
        ['link', { rel: 'shortcut icon', type: "image/x-icon", href: './favicon.ico' }]
    ],
    evergreen: true,
    editLinkText:'在 GitHub 上编辑此页',
    port: 8081,
    ga: 'UA-71886989-13',
    themeConfig: {
        repo: 'microapp-store/flash-waimai',
        docsDir: 'doc',
        editLinks: true,
        editLinkText: '编辑此页面！',
        nav: [
            {text: '文档', link: '/'},
            {text: '资源',link:'/resource'},
            {text: '捐赠',link:'/donate'},
            {text: 'Gitee', link: 'https://gitee.com/microapp/flash-waimai'},


        ],
        sidebar: [
            {
                title: '基本准备',
                collapsable: false,
                children: [
                    '/base/jdkAndMaven',
                    '/base/modules'
                ]
            },
            {
                title: '将本项目在跑起来',
                collapsable: false,
                children: [
                    '/quickstart/quickstart',
                    '/quickstart/clone',
                    '/quickstart/initDb',
                    '/quickstart/config',
                    '/quickstart/startup'
                ]
            },
              {
                title: '其他',
                collapsable: false,
                children:[
                    '/other/faq'
                ]
            }
        ]

    }
}
