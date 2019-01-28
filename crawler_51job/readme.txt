https://search.51job.com/list/010000,000000,0000,00,9,99,java,2,【分页信息】2.html?
lang=c&stype=1&postchannel=0000&workyear=99&cotype=99&degreefrom=99&jobterm=99&companysize=99
&lonlat=0%2C0&radius=-1&ord_field=0&confirmdate=9&fromType=&dibiaoid=0&address=&line=
&specialarea=00&from=&welfare=


https://search.51job.com/list/010000,000000,0000,00,9,99,java,2,【分页信息】3.html?
lang=c&stype=1&postchannel=0000&workyear=99&cotype=99&degreefrom=99&jobterm=99&companysize=99
&lonlat=0%2C0&radius=-1&ord_field=0&confirmdate=9&fromType=&dibiaoid=0&address=&line=
&specialarea=00&from=&welfare=
循环生成分页url
spider 添加分页列表url

Processor:
if 列表页 （正则匹配列表页  regex: /https://search\.51.job\.com/list/.*\.html/）
    爬详情页链接
    添加URL
    #添加下一分页url   可在此设置页数限制

if 详情页
    爬数据
    添加至管道  持久化到db