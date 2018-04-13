# ListView分组固定指定postion的convertView在顶部。

ListView滑动的时候，如果我们对ListView做了分组，可以把某些分组固定在ListView的顶部。

- 重写ListView（SectionPinListView）实现分组悬浮列表，不破坏ListView原有的所有功能。
- 重写BaseAdapter（SectionPinAdapter）尽量减少BaseAdapter的负担，在里面确定哪些position的convetView要固定在顶部。
- 可以固定指定的position的convetView在ListView的顶部。
- 对ListView上拉刷新，下拉加载更多的时候分组悬浮还有效果。
- SectionPinListView既可以设置重写的SectionPinAdapter，也可以不设置，如果设置的不是重写的SectionPinAdapter是没有分组悬浮的效果。

效果

![这里写图片描述](http://img.blog.csdn.net/20170422213857897?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvd3V5dXhpbmcyNA==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

博客链接地址

http://blog.csdn.net/wuyuxing24/article/details/70477566
