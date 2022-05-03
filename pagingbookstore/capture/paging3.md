### 流

1. 冷流/热流

   > Flow是冷流，什么是冷流？简单来说，如果Flow有了订阅者Collector以后，发射出来的值才会实实在在的存在于内存之中，这跟懒加载的概念很像。
   >
   > 与之相对的是热流，StateFlow和SharedFlow是热流，在垃圾回收之前，都是存在内存之中，并且处于活跃状态的。

​    

2. StateFlow

   StateFlow是一个状态容器式可观察数据流可以向其收集器发出当前状态更新
   和新状态更新。还可通过其value属性读取当前状态值。

   ```kotlin
   class NumViewModel : ViewModel() {
   
       val number = MutableStateFlow(0)
   
       fun increment() {
           number.value++
       }
   
       fun decrement() {
           number.value--
       }
   }
   
   //...
    lifecycleScope.launchWhenCreated {
          viewModel.number.collect { value ->
               mBinding.tvResult.text = "$value"
          }
    }
   //...
   ```

   

3. SharedFlow 

   SharedFlow会向从其中收集值的所有使用方发出数据。



### Paging3分页加载

1. 加载流程：

   

**Repository**【*PagingSource*】     ⋙    **ViewModel**【*Pager* >> *Flow<PagingData>*】   ⋙    **UI**【*PagingDataAdapter*】



2. PageConfig

   > pageSize,每页显示的数据的大小。
   >
   > prefetchDistance,预刷新的距离,距离最后十个item多远时加载数据,默认为pageSize。
   > initialLoadSize,初始化加载数量,默认为pageSize*3。

3. Room支持

   > 如果使用的是Room,从2.3.0-alpha开始,它将默认为您实现PagingSource。
   > 在定义Dao接口的Query 语句时，返回类型要使用PagingSource类型。同时
   > 不需要在Query里指定页数和每页展示数量，页数由PagingSource来控制，
   > 每页数量页在PagingConfig中定义。

4. LoadType

   > LoadType是一个枚举类，里面定义了三个值，如下所示：
   >
   > | 类名             | 作用                                                         |
   > | :--------------- | ------------------------------------------------------------ |
   > | LoadType.Refresh | 在初始化刷新的使用,首次访问或者调用PagingDataAdapter.refresh()触发 |
   > | LoadType.Append  | 在加载更多的时候使用                                         |
   > | LoadType.Prepend | 在当前列表头部添加数据的时候使用，需要注意的是当LoadType.REFRESH触发了，LoadType.PREPEND也会触发 |



5. PagingState
   pages:List<Page<Key,Value>>返回的上一页的数据,主要用来获取上一页
   最后一条数据作为下一页的开始位置。
   config:PagingConfig 返回的初始化设置的PagingConfig 包含了pageSize、
   prefetchDistance、initialLoadSize 等等。

6. MediatorResult

   > 请求出现错误，返回MediatorResult.Error(e)。
   > 请求成功且有数据，返回MediatorResult.Success(endOfPaginationReached
   > = true)。
   > 请求成功但是没有数据，返回MediatorResult.Success(endOfPaginationReac
   > hed = false)。

### Coil

1. 特点

> 性能优秀
> 体积较小：其包体积与Picasso相当，显著低于Glide和Fresco，仅仅只有1500个方法，但是在功能上却不输于其他同类库。
>
> 简单易用：配合Kotlin扩展方法等语法优势，APl简单易用。
> 技术先进：基于Coroutine、OkHttp、Okio、AndroidX等先端技术开发，确保了技术的先进性。
> 丰富功能：缓存管理（MemCache、DiskCache)、动态采样（Dynamic image samping）、加载中暂停/终止等功能有助于提高图片加载效率。

