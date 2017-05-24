# AndroidPopularDesignPatterns
android-mvp&amp;mvvm分别实现列表加载-demo

![design.jpg](http://upload-images.jianshu.io/upload_images/3093487-888da1beca7f925e.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


Mvp与Mvvm已经出现很久了，但是还有很多开发者没有运用到自己的项目中。正好最近工作不是很忙，这两天空余时间就写了个demo，来给还没有运用到实战项目的小伙伴一个参考，如果有什么问题可以直接给我留言，Ok我们进入正题~   

**先看一下效果图（由于平台上传gif图有大小限制，所以就分成两个gif上传的）**


![mvp.gif](http://upload-images.jianshu.io/upload_images/3093487-ed87a1a1e381b7d5.gif?imageMogr2/auto-orient/strip)


![mvvm.gif](http://upload-images.jianshu.io/upload_images/3093487-1880b7e9fd80ddb0.gif?imageMogr2/auto-orient/strip)




**先简单介绍一下demo**
> 为了方便，demo中下拉刷新上拉加载直接用的xRecyclerView库。列表类似于新闻客户端多布局模式(无图-1张图-多图)。以及app里非常常见的点赞功能，分别在mvp设计模式与mvvm设计模式中实现。无论是mvp还是mvvm或其他的设计模式，建议都不要生搬硬套，灵活的运用和多变的实现才是掌握一种设计思想的最高境界（当然前提是不能脱离所运用的模式）。 

**包结构**

![package.png](http://upload-images.jianshu.io/upload_images/3093487-3af4da7804a2e2fb.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

mvp与mvvm分为两个包，看代码的时候可以分开看，common 里边是简单封装的BaseAdapter，感兴趣的可以下载源码看看，这里就不贴代码了。下面的讲解中，每一个功能点对应的mvp实现方式与mvvm实现方式我会对照着来讲。本篇文章只针对demo中所涉及到的知识点来讲。

**ok到了我们最重要的部分**


![mvp.jpg](http://upload-images.jianshu.io/upload_images/3093487-78e2bfcfc108af24.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![mvvm.jpg](http://upload-images.jianshu.io/upload_images/3093487-ee34c6922ecae65b.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

> mvp与mvvm都是非常棒的设计模式，但是都各有利弊，在这里我就不长篇大论的去墨迹了，相信你们已经多少有点了解了。

**先来说一下Mvp**

从上面的mvp.jpg图中我们可以看到，mvp分为三层，分别为Model、View、Presenter。
```
1.view层调用presenter。
2.presenter收到view的信号，调用具体的业务逻辑model层。
3.model层收到了presenter的信号，开始进行具体的业务逻辑处理。
比如(网络请求-数据库存取...)
4.model层 将具体的业务逻辑处理完之后通过接口回调的形式
将结果返回给presenter。
5.presenter收到model返回的结果同样以接口的形式返回给
view层做相应的显示
```

**Mvvm**
从上面的mvvm.jpg图中我们可以看到，mvvm也是分为三层，分别为Model、View、ViewModel。
```
说到mvvm设计模式，就要涉及到一个库 databinding，
在AS中 使用起来也是比较方便的
直接在module:app中的 build.gradle中打开databinding的支持就可以了

android {
    ....
    dataBinding {
        enabled = true
    }
}

```

具体的详解可以看google的开发文档(demo中所涉及到的databinding的知识点我会讲解)
<a href="https://developer.android.com/topic/libraries/data-binding/index.html">Data Binding</a>

```
1.view层接收到用户的操作传递给viewModel层
(View层与ViewModel 通过dataBinding 
实现数据与view的单向绑定或双向绑定)
2.ViewModel 层接收到用户传来的信号，调用model层。
3.model层收到viewModel的信号进行具体的业务逻辑处理。
4.model层将结果通过接口的形式传递给viewModel层 
5.因为viewModel与view通过databinding实现了绑定，viewModel接收到model传来的结果做出相应的view展示
```

> 通过上边的大白话描述~ 我们可以看到 mvp中 presenter 起到了桥接的作用，view 层 与model层的通讯 通过中间量presenter 以接口的形式进行传递。mvvm中 viewModel 和presenter 的作用类似 ，只不过是通过 databinding 将数据与ui进行了绑定。

**mvvm中 xml 对应的格式**
```
<layout
 xmlns:android="http://schemas.android.com/apk/res/android">
    <data>
        <variable
            name="name"
            type="type"/>
    </data>
<ViewGroup></ViewGroup>
</layout>
```
与以往的xml形式不同。最外层为 layout，里边有data标签 ，data标签里有variable变量属性 。ViewGroup标签中的内容才和我们之前的xml是一样的。
所有以layout为根标签的布局都会生成对应的ViewDataBinding 类，比如 MainActivity的xml名字为 activity_main ,databinding就会自动为我们生成 ActivityMainBinding。

**获取ActivityMainBinding的方法**
```
在MainActivity 的oncreate方法中获取
ActivityMainDatabinding

ActivityMainBinding binding = 
DataBindingUtil.setContentView
(this, R.layout.activity_main);

```
之前设置布局文件的方式为setContentView（layoutId）

用databinding设置布局文件统一通过DataBindingUtil类来设置


**下面看一下demo中具体的代码实现**

**mvvm中BaseActivity的实现**
![mvvm_base_activity.png](http://upload-images.jianshu.io/upload_images/3093487-3fa276cc556a26a4.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![mvvm_base_activity_oncreate.png](http://upload-images.jianshu.io/upload_images/3093487-4bf4d51e5fa384c6.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

从图中可以看出 我们通过泛型 VDB 来指定ViewDatabinding的类型。

**mvp中BaseActivity的实现**


![mvp_base_activity.png](http://upload-images.jianshu.io/upload_images/3093487-51f41d0b3c6d426c.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

在mvp中我们依然通过泛型来指定IView接口 与 Presenter 的类型（方便在presenter中绑定Activity或Fragment的生命周期）,presenter方便做attach 与 detach的操作。

**MvvmActivity中的代码**

```

/**
 * Created by mj
 * on 2017/5/22.
 */
public class MvVmActivity extends BaseMvVmActivity<ActivityMvVmBinding> implements XRecyclerView.LoadingListener {

    /**
     * 新闻列表 adapter
     */
    NewsListAdapter adapter;
    /**
     * view model
     */
    NewsListVm newsListVm;

    @Override
    public int getLayoutId() {
        return R.layout.activity_mv_vm;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        initAdapter();
        initVM();
    }

    /**
     * 初始化adapter
     */

    private void initAdapter() {
        XRecyclerView xRecyclerView = viewDataBinding.activityMvVmList;

        xRecyclerView.setLoadingListener(this);
        xRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        xRecyclerView.setArrowImageView(R.mipmap.pull_down_arrow);
        xRecyclerView.setRefreshProgressStyle(ProgressStyle.BallClipRotate);
        xRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallClipRotate);

        adapter = new NewsListAdapter();
        xRecyclerView.setAdapter(adapter);
    }

    /**
     * 初始化viewModel
     */
    private void initVM() {
        newsListVm = new NewsListVm(context,viewDataBinding, adapter);
    }

    @Override
    public void onRefresh() {
        newsListVm.setRefreshData();
    }

    @Override
    public void onLoadMore() {
        newsListVm.loadMoreData();
    }


}

```
在mvvm中我们把用户对view的操作以及UI的样式 放在 activity中或xml中，在上面的代码中我们可以看到，下拉刷新和上拉加载以及 刷新的样式 都放到Activity中，在下拉刷新和上拉加载的代码中通过newsListVm (viewModel的实例）来调用刷新数据和加载数据 

**MvpActivity中的代码**

```
/**
 * Created by mj
 * on 2017/5/22.
 */

public class MvpActivity extends BaseMvpActivity<INewsListView, NewsListPresenterImpl> implements INewsListView, XRecyclerView.LoadingListener {
    /**
     * 首次加载
     */
    public static final int FIRST_LOAD = 0;
    /**
     * 刷新
     */
    public static final int REFRESH = 1;
    /**
     * 加载更多
     */
    public static final int LOAD_MORE = 2;
    /**
     * recyclerView
     */
    @BindView(R.id.mvp_list_view)
    XRecyclerView recyclerView;
    /**
     * adapter
     */
    NewsListAdapter adapter;
    /**
     * 页数
     */
    private int pageNum = 1;

    @Override
    public NewsListPresenterImpl initPresenter() {
        return new NewsListPresenterImpl();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_mvp;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        initAdapter();
        firstLoadData();
    }

    /**
     * 初始化adapter
     */
    private void initAdapter() {
        LinearLayoutManager lm = new LinearLayoutManager(context);
        recyclerView.setLoadingListener(this);
        recyclerView.setLayoutManager(lm);
        recyclerView.setArrowImageView(R.mipmap.pull_down_arrow);
        recyclerView.setRefreshProgressStyle(ProgressStyle.BallPulseSync);
        recyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallPulseSync);

        adapter = new NewsListAdapter();
        recyclerView.setAdapter(adapter);
    }

    /**
     * 首次加载数据
     */
    private void firstLoadData() {
        presenter.loadNewsList(FIRST_LOAD, pageNum);
    }

    /**
     * 刷新数据
     */
    private void refreshData() {
        pageNum = 1;
        presenter.loadNewsList(REFRESH, pageNum);
    }

    /**
     * 加载更多
     */
    private void loadMoreData() {
        pageNum++;
        presenter.loadNewsList(LOAD_MORE, pageNum);
    }

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        refreshData();
    }

    /**
     * 加载更多
     */

    @Override
    public void onLoadMore() {
        loadMoreData();
    }

    /**
     * 加载成功回调
     *
     * @param data     列表数据
     * @param loadType 加载类型
     */
    @Override
    public void loadSuccess(List<NewsEntity> data, int loadType) {
        switch (loadType) {
            case FIRST_LOAD:
                adapter.refreshData(data);
                recyclerView.refreshComplete();
                break;
            case REFRESH:
                adapter.refreshData(data);
                recyclerView.refreshComplete();
                break;
            case LOAD_MORE:
                adapter.loadMoreData(data);
                recyclerView.loadMoreComplete();
                break;
        }
    }

    /**
     * 加载出错
     *
     * @param msg 错误信息
     */
    @Override
    public void showError(String msg) {
        ToastUtils.show(context, msg);
        if (pageNum > 1) {
            pageNum--;
        }
        recyclerView.refreshComplete();
        recyclerView.loadMoreComplete();
    }

    /**
     * 展示加载进度
     *
     * @param msg 加载信息
     */
    @Override
    public void showLoading(String msg) {
        PromptDialog.getInstance().show(context,msg);
    }

    /**
     * 关闭加载进度
     */
    @Override
    public void hideLoading() {
        PromptDialog.getInstance().close();
    }

}
```
在mvp的activity中我们通过presenter的实例来调用刷新或加载更多并且处理相应的view显示 （PromptDialog 为Utils中封装的一个简单系统加载框）

**mvvm中的viewModel 代码**

```
/**
 * Created by mj
 * on 2017/5/22.
 * viewModel
 */

public class NewsListVm implements INewsListModel.LoadResponse {
    /**
     * 首次加载
     */
    private final int FIRST_LOAD = 0;
    /**
     * 下拉刷新
     */
    private final int REFRESH = 1;
    /**
     * 加载更多
     */
    private final int LOAD_MORE = 2;
    /**
     * binding
     */
    private ActivityMvVmBinding binding;
    /**
     * adapter
     */
    private NewsListAdapter adapter;
    /**
     * 加载列表数据业务逻辑
     */
    private NewsListModelBiz newsListModelBiz;
    /**
     * 页数
     */
    private int pageNum = 1;
    /**
     * context
     */
    private Context context;

    public NewsListVm(Context context, ActivityMvVmBinding binding, NewsListAdapter adapter) {
        this.context = context;
        this.binding = binding;
        this.adapter = adapter;
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        newsListModelBiz = new NewsListModelBiz();
        firstLoadData();
    }

    /**
     * 首次加载
     */
    private void firstLoadData() {
        PromptDialog.getInstance().show(context, "加载中...");
        newsListModelBiz.load(FIRST_LOAD, pageNum, this);
    }

    /**
     * 刷新数据
     */
    public void setRefreshData() {
        pageNum = 1;
        newsListModelBiz.load(REFRESH, pageNum, this);
    }

    /**
     * 加载更多
     */
    public void loadMoreData() {
        pageNum++;
        newsListModelBiz.load(LOAD_MORE, pageNum, this);
    }

    @Override
    public void loadSuccess(List<NewsEntity> data, int loadType) {
        switch (loadType) {
            case FIRST_LOAD:
                adapter.refreshData(data);
                binding.activityMvVmList.refreshComplete();
                break;
            case REFRESH:
                adapter.refreshData(data);
                binding.activityMvVmList.refreshComplete();
                break;
            case LOAD_MORE:
                adapter.loadMoreData(data);
                binding.activityMvVmList.loadMoreComplete();
                break;
        }
        PromptDialog.getInstance().close();
    }

    @Override
    public void loadFailure(String msg) {
        // 加载失败后的提示
        if (pageNum > 1) {
            pageNum--;
        }
        PromptDialog.getInstance().close();
        binding.activityMvVmList.refreshComplete();
        binding.activityMvVmList.loadMoreComplete();
    }

}
```
viewModel中的代码也比较简单 主要是调用model层  加载数据 以及对model层返回的结果进行显示。

**mvp中的presenter 代码**

```
/**
 * Created by mj
 * on 2017/5/22.
 */

public class NewsListPresenterImpl extends BasePresenter<INewsListView> implements INewsListPresenterBiz, INewsListModelBiz.LoadResponse {
    /**
     * 加载列表数据的业务逻辑处理
     */
    private INewsListModelBiz iNewsListModelBiz;

    public NewsListPresenterImpl() {
        iNewsListModelBiz = new NewsListModelImpl();
    }

    @Override
    public void loadNewsList(int loadType,int pageNum) {
        if (mView != null) {
            // 首次进入界面展示加载对话框
            if (loadType == MvpActivity.FIRST_LOAD) {
                mView.showLoading("加载中...");
            }
            iNewsListModelBiz.load(loadType,pageNum, this);
        }
    }

    @Override
    public void loadSuccess(List<NewsEntity> data, int loadType) {
        if (mView != null) {
            mView.loadSuccess(data, loadType);
            mView.hideLoading();
        }
    }

    @Override
    public void loadFailure(String msg) {
        if (mView != null) {
            mView.showError(msg);
            mView.hideLoading();
        }
    }


}
```

与mvvm中的viewModel类似 都是调用 model层 以及对model层返回的结果做UI显示。omg~  感觉这样粘上来的代码使文章变得很长~~~ 我尽量简化一下，大家可以下载demo具体的看一下实现过程。

model层我就不再粘贴代码了~   mvp中的model 层 与 mvvm中的model层是一样的，都是做数据的准备 ，以及通过回调形式将数据返回。接下来我们看一下mvvm中 的adapter 的实现方式

**Mvvm Adapter中的代码**

```
/**
 * Created by mj
 * on 2017/5/22.
 */

public class NewsListAdapter extends BaseAdapter<NewsEntity, BindingVH> {

    /**
     * 没有图片的item 类型
     */
    private final int NO_PIC = 0;
    /**
     * 有一张图片的item 类型
     */
    private final int ONE_PIC = 1;
    /**
     * 更多图片的item 类型
     */
    private final int MORE_PIC = 2;

    /**
     * 根据图片数量判断item 的类型
     *
     * @param position position
     * @return itemType
     */
    @Override
    public int getItemViewType(int position) {
        if (data.get(position).getPicNum() == 0) {
            return NO_PIC;
        } else if (data.get(position).getPicNum() == 1) {
            return ONE_PIC;
        } else {
            return MORE_PIC;
        }
    }

    @Override
    public BindingVH createVH(ViewGroup parent, int viewType) {
        ViewDataBinding viewDataBinding = null;
        switch (viewType) {
            case NO_PIC:
                viewDataBinding = DataBindingUtil.inflate(inflater, R.layout.mv_vm_item_text, parent, false);
                break;
            case ONE_PIC:
                viewDataBinding = DataBindingUtil.inflate(inflater, R.layout.mv_vm_item_one_pic, parent, false);
                break;
            case MORE_PIC:
                viewDataBinding = DataBindingUtil.inflate(inflater, R.layout.mv_vm_item_more_pic, parent, false);
                break;
        }
        return new BindingVH<>(viewDataBinding);
    }

    @Override
    public void bindVH(BindingVH bindingVH, int position) {
        bindingVH.getBinding().setVariable(BR.newsEntity, data.get(position));
        bindingVH.getBinding().setVariable(BR.handle, this);
        bindingVH.getBinding().setVariable(BR.position, position);

        bindingVH.getBinding().executePendingBindings();
    }

    /**
     * 点赞
     *
     * @param newsEntity entity
     */
    public void thumbUpClick(NewsEntity newsEntity, int position) {

        if (newsEntity.isNice()) {
            newsEntity.setNice(false);
            newsEntity.setNiceCount(newsEntity.getNiceCount() - 1);
            ToastUtils.show(context, "取消点赞 position=" + position);

        } else {
            newsEntity.setNice(true);
            newsEntity.setNiceCount(newsEntity.getNiceCount() + 1);
            ToastUtils.show(context, "点赞成功 position=" + position);
        }

    }


}
```   

> 实现一个3种item形式的adapter,仅仅用了不到100行的代码(其中还包括空行和注释) 主要归功于 databinding 为我们承受了成吨的伤害~ 。
createVH(ViewGroup parent, int viewType)方法与
bindVH(BindingVH bindingVH, int position)方法都是BaseAdapter中的抽象类 和recyclerview.Adapter中的onCreateViewHolder()与onBindViewHolder()是一样的。


我们先来看一下 createVH方法中 返回了一个BindingVH的对象，并且根据不同的viewType 返回了不同的ViewDataBinding，那么 这个BindingVH 一定是ViewDataBinding的子类啦 ~ ，继续上代码~   

```
/**
 * Created by mj
 * on 2017/5/22.
 * binding view holder
 */

public class BindingVH<B extends ViewDataBinding> extends RecyclerView.ViewHolder {
    /**
     * viewDataBinding
     */
    private B mBinding;

    /**
     * constructor
     *
     * @param binding viewDataBinding
     */
    public BindingVH(B binding) {
        super(binding.getRoot());
        mBinding = binding;
    }

    /**
     * @return viewDataBinding
     */
    public B getBinding() {
        return mBinding;
    }

}
```

BindingVH继承自RecyclerView.ViewHolder 这个类，并且提供了获取binding的方法 。

我们继续回到createVH这个方法中。在这里 我们设置布局的方式是通过 DatabindingUtil.inflate(...) 方法获取的ViewDataBinding，我们在看一下 多图布局的xml布局(另外两个xml都类似 我们就针对一个来说吧~)
```
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:app="http://schemas.android.com/apk/res-auto">

    <data>

        <import type="com.mj.design_patterns.R"/>

        <import type="android.view.View"/>

        <variable
            name="newsEntity"
            type="com.mj.design_patterns.mv_vm.bean.NewsEntity"/>

        <variable
            name="handle"
            type="com.mj.design_patterns.mv_vm.adapter.NewsListAdapter"/>

        <variable
            name="position"
            type="int"/>

    </data>

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:background="@color/white"
        android:orientation="vertical">

        <TextView
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginLeft="10dip"
            android:layout_marginRight="10dip"
            android:layout_marginTop="10dip"
            android:ellipsize="end"
            android:maxLines="1"
            android:text="@{newsEntity.content}"
            android:textColor="@color/c3"
            android:textSize="15sp"/>

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="60dip"
            android:layout_margin="10dip">

            <ImageView
                android:layout_width="0dip"
                android:layout_height="match_parent"
                android:layout_marginRight="5dip"
                android:layout_weight="1"
                android:scaleType="centerCrop"
                app:imageUrl="@{newsEntity.imageUrls[0]}"/>

            <ImageView
                android:layout_width="0dip"
                android:layout_height="match_parent"
                android:layout_weight="1"
                android:scaleType="centerCrop"
                app:imageUrl="@{newsEntity.imageUrls[1]}"/>

            <ImageView
                android:layout_width="0dip"
                android:layout_height="match_parent"
                android:layout_marginLeft="5dip"
                android:layout_weight="1"
                android:scaleType="centerCrop"
                android:visibility="@{newsEntity.getPicNum == 2 ? View.GONE : View.VISIBLE}"
                app:imageUrl="@{newsEntity.imageUrls[2]}"/>

        </LinearLayout>


        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginBottom="10dip"
            android:layout_marginLeft="10dip"
            android:layout_marginRight="10dip"
            android:gravity="center_vertical"
            android:orientation="horizontal">

            <TextView
                android:layout_width="0dip"
                android:layout_height="wrap_content"
                android:layout_weight="1"
                android:text="@{newsEntity.getDateSplicingPageNum}"
                android:textColor="@color/c6"
                android:textSize="12sp"/>

            <!--android:src="@{newsEntity.isNice ? @drawable/nice_press :@drawable/nice_normal}"-->

            <ImageView
                android:layout_width="15dip"
                android:layout_height="15dip"
                android:onClick="@{()->handle.thumbUpClick(newsEntity,position)}"
                app:resId="@{newsEntity.isNice ? R.mipmap.nice_press : R.mipmap.nice_normal}"/>

            <TextView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_marginLeft="5dip"
                android:text="@{String.valueOf(newsEntity.niceCount)}"
                android:textColor="@{newsEntity.isNice ? @color/appColor : @color/c6}"
                android:textSize="12sp"/>


        </LinearLayout>

        <View
            android:layout_width="match_parent"
            android:layout_height="1px"
            android:background="@color/listBgC"/>
    </LinearLayout>
</layout>

```

在这个xml布局中我们在结合adapter类一起来说一下demo中的数据以及事件是如何绑定到xml中的。其实在adapter中每一个item都相当于一个ViewModel 

我们就从布局中的信息从上往下说吧~  xml中的格式我前面已经说过了 ，就不在赘述了。

在data标签中 我们看到了import  没错在这里我们可以引入包~  (在使用databinding时除了java.lang的包不需要import 其他xml中用到的都需要import进来)

variable 标签中 name属性的值是你随意定义的(前提是你要知道定义这个名字的意义) type属性就是我们需要导入的包了~  比如我们用到了 NewsEntity这个实体类 我们就需要导入这个包，可以这个实体是怎么设置进来的呢？ 我们来看adapter中的bindVH方法

![bind_vh.jpg](http://upload-images.jianshu.io/upload_images/3093487-8c2e5dc6c7e8c6f4.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

我们通过viewDataBinding 把数据或事件传递进去。因为我们没有 获取具体的binding类型，所以我们通过调用setVariable（a,b）来设置。
a代表：通过BR类来查找xml中variable标签中属性name定义的名字
b代表：事件或数据

否则可以通过具体的binding类型实例来设置，比如
![set_var.jpg](http://upload-images.jianshu.io/upload_images/3093487-d1156704982497a2.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

最后通过viewDatabinding.executePendingBindings(); 来实现绑定。
看上边xml布局  文本的绑定方式为：

![binding_text.jpg](http://upload-images.jianshu.io/upload_images/3093487-688d5928af85006a.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

所有xml中无论是事件绑定还是数据绑定都要这种格式@{...}
因为我们已经设置了newsEntity 所以在我画圈的地方可以直接使用

图片的绑定：

![binding_image.jpg](http://upload-images.jianshu.io/upload_images/3093487-4dfd916000eecd39.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

可是我们 实体里边肯定是存的一个图片地址啊~~  这怎么实现绑定呢！ 没关系 databinding已经给我们提供了方法：通过添加@BindingAdapter注解 databinding 就会为我们生成一个 attr对应的属性

```
/**
 * Created by mj
 * on 2017/5/22.
 */

public class BindImageAdapter {
    /**
     * mv_vm xml 传入url 加载图片
     * imageUrl 为xml中 的命名
     *
     * @param iv   imageView
     * @param path 图片路径
     */
    @BindingAdapter({"imageUrl"})
    public static void loadImage(ImageView iv, String path) {
        Glide.with(iv.getContext()).load(path).into(iv);
    }

    /**
     * mv_vm xml 设置 mipmap Resource
     *
     * @param iv    imageView
     * @param resId resource id
     */
    @BindingAdapter({"resId"})
    public static void loadMipmapResource(ImageView iv, int resId) {
        iv.setImageResource(resId);
    }

}
```
我们将图片地址传进来 我们在用图片加载框架对其进行加载。
@BindingAdapter({"imageUrl"}) imageUrl就是 对应的xml中 自定义的属性名字。这样就实现了图片的绑定。我们实体类中是 imageUrls 是一个string数组类型 所以 我们在xml中就直接可以使用下标的方式获取图片的url(顺便提一下 ，在xml实现绑定时 我们根本不用担心数组越界 对象为空这些~~  因为 databinding已经为我们做好了处理)

接下来我们看一下点赞的事件绑定：


![event_binding.jpg](http://upload-images.jianshu.io/upload_images/3093487-16b8382db0c55aad.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

喔~  这里支持lambda 写法 前面已经写过了 在bindVH方法中设置handle 所以我们这里可以直接用。看handle 所指向的方法在adapter中的实现 
![event_method.jpg](http://upload-images.jianshu.io/upload_images/3093487-75f19be51d5c2488.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

在xml中的onClick方法直接指向了adapter的thumbUpClick方法~  ，以及参数也是在xml中传递过来的~~    那么看看我们的点赞 到底是如何实现双向绑定的呢~~ 相信很多同学刚才已经看到了 上图中ImageView中的resId属性了~  。里边写了一个三元表达式，也就是说 如果 newEntity中的 isNice为true 就显示 聚焦状态的点赞图片，否则显示正常的点赞图片
在newsEntity 类里继承了 dataBinding 的 BaseObservable，并且看下图：

![entity.jpg](http://upload-images.jianshu.io/upload_images/3093487-77a48ed59d9ff736.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

isNice方法添加了Bindable 注释 并且 setNice方法里边调用了notifyChange()方法 。这样就实现了双向绑定 数据改变UI改变 UI改变数据也随之改变的逻辑~~ 。(还有其他的方式 我就不赘述了，请看google文档，前面已经给了传送门)。

吐槽一下 databinding 居然不支持在xml中设置@mipmap，只支持@drawable。   所以有心的同学在上面已经看到了  我们是通过@BindingAdapter方式来实现的（可以上滑看一下）

看上面的gif图 日期后边我拼接了一个当前页数。这里在啰嗦一下 在xml中使用字符串的拼接~  (xml不允许写入中文，否则报错)
我们可以这样实现拼接
```
android:text="@{@string/listPageNumDisplay(newsEntity.dateStr,newsEntity.pageNum)}"

在strings.xml中定义替换
<string name="listPageNumDisplay">%1$s--##第%2$d页数据</string>

但是在我们的项目中 没有使用这种方式
而是直接在实体类里边（NewsEntity）单提出一个方法在xml方便调用

/**
     * 获取日期拼接页数的字符串
     * @return 格式--> 2017年5月23日--##第1页数据
     */
    public String getDateSplicingPageNum(){
        return dateStr+"--##第"+pageNum+"页数据";
    }

实现方式有很多种~  大家可以动手去尝试一下
```

mvp中的adapter实现我就不再粘贴了~   建议还是直接下载代码来看，比较直观方便(有什么问题可以给我留言)我就不再多啰嗦了


