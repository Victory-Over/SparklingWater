## SparklingWater 气泡水

#### 1、添加依赖和配置
* 根目录build.gradle文件添加如下配置：

```Java
allprojects {
    repositories {
       	maven { url 'https://jitpack.io' }
    }
}
```

* APP目录build.gradle文件添加如下配置：

```Java
dependencies {
     implementation 'com.github.Victory-Over:SparklingWater:v1.0.0
}
```

#### 2、效果展示
* 锅炉特效

![点我查看效果图](https://github.com/Victory-Over/SparklingWater/blob/master/file_boiler.gif)

* 水浪特效

![点我查看效果图](https://github.com/Victory-Over/SparklingWater/blob/master/file_bubble.gif)


#### License

```
Copyright [2018] [Victory-Over]

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
