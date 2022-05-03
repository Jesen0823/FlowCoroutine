

1. Compose的使用：

```Kotlin
   @Composable
   fun CoilImage(){
   	Box(
   		modifier = Modifier
   			.height(150.dp)
   			.width(150.dp),
   		contentAlignment = Alignment.Center
   	){
   		val painter = rememberImagePainter(
   			data = "https://avatars.githubusercontent.com/u/14994036?v=4",
   			builder = {
   			    // 占位图
   				placeholder(R.drawable.ic_placeholder)
   				error(R.drawable.ic_error)
   				// 淡入效果
   				crossfade(durationMillis: 1808)
   				transformations(
   				    // 圆角样式
   					GrayscaleTransformation(),
   					// 圆角大小
   					RoundedCornersTransformation(50f)
   					// 圆形样式
   					CircleCropTransformation(),
   					// 高斯模糊
   					BlurTransformation(LocalContext.current)
                   )
               }
           }
           val painterState = painter.state
           Image(painter = painter, contentDescription ="Logo Image")
           // 监听加载状态
           if(painterState is ImagePainter.State.Loading)(
               CircularProgressIndicator()
           )
       }
   }

```

2. 常规Activity中的使用
```Kotlin

    fun bindingAvatar(imageView: ImageView,url:String?){
        imageView.load(url){
            crossfade(true) // 淡入淡出
            placeholder(R.drawable.ic_cache_image)
            transformations(
                // 圆角样式
               	GrayscaleTransformation(),
               	// 圆角大小
               	RoundedCornersTransformation(50f)
               	// 圆形样式
               	CircleCropTransformation(),
               	// 高斯模糊
               	BlurTransformation(LocalContext.current)
            )
        }
    }
```

