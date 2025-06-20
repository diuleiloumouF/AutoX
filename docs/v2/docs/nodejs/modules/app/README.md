# app

app模块提供一系列函数，用于使用其他应用、与其他应用交互。
例如发送意图、打开文件、发送邮件等。
同时提供了方便的进阶函数startActivity和sendBroadcast，
用他们可完成app模块没有内置的和其他应用的交互。

要导入此模块，请使用语句`import * as app from 'app'`，
或者导入本模块的部分函数、属性`import { startActivity, packageName } from 'app';`

## 示例

```js
import { startActivity, sendEmail } from 'app'
startActivity({
    packageName: 'org.autojs.autoxjs.v7',
    className: 'org.autojs.autojs.ui.settings.SettingsActivity'
  })
```

## 接口

- [EmailOptions](interfaces/EmailOptions.md)
- [IntentOptions](interfaces/IntentOptions.md)
- [PMOptions](interfaces/PMOptions.md)

## 变量

### packageName

```ts
const packageName: string;
```

定义于: src/app/index.ts:28

当前应用的包名。

## 函数

### editFile()

```ts
function editFile(file): void
```

定义于: src/app/index.ts:95

用其他应用编辑文件。文件不存在的情况由编辑文件的应用处理。
如果找不出可以编辑该文件的应用，则抛出`ActivityNotException`。

#### 参数

| 参数 | 类型 | 描述 |
| ------ | ------ | ------ |
| `file` | `string` | 文件路径 |

#### 返回

`void`

***

### getApkInfo()

```ts
function getApkInfo(file, flags?): PackageInfo
```

定义于: src/app/index.ts:98

#### 参数

| 参数 | 类型 |
| ------ | ------ |
| `file` | `string` |
| `flags`? | `number` \| [`PMOptions`](interfaces/PMOptions.md) |

#### 返回

`PackageInfo`

***

### getAppName()

```ts
function getAppName(packageName): null | string
```

定义于: src/app/index.ts:110

获取应用包名对应的已安装的应用的名称。如果该找不到该应用，返回 null。

#### 参数

| 参数 | 类型 | 描述 |
| ------ | ------ | ------ |
| `packageName` | `string` | 应用包名 |

#### 返回

`null` \| `string`

***

### getInstalledApps()

```ts
function getInstalledApps(flags?): PackageInfo[]
```

定义于: src/app/index.ts:120

#### 参数

| 参数 | 类型 |
| ------ | ------ |
| `flags`? | `number` \| [`PMOptions`](interfaces/PMOptions.md) |

#### 返回

`PackageInfo`[]

***

### getInstalledPackages()

```ts
function getInstalledPackages(flags?): PackageInfo[]
```

定义于: src/app/index.ts:114

#### 参数

| 参数 | 类型 |
| ------ | ------ |
| `flags`? | `number` \| [`PMOptions`](interfaces/PMOptions.md) |

#### 返回

`PackageInfo`[]

***

### getPackageName()

```ts
function getPackageName(targetAppName): null | string
```

定义于: src/app/index.ts:127

获取应用名称对应的已安装的应用的包名，如果该找不到该应用，返回 null 。
如果该名称对应多个应用，则只返回其中某一个的包名。

#### 参数

| 参数 | 类型 | 描述 |
| ------ | ------ | ------ |
| `targetAppName` | `string` | app名称 |

#### 返回

`null` \| `string`

***

### getUriForFile()

```ts
function getUriForFile(pathOrUri): Uri
```

定义于: src/app/index.ts:136

从一个文件路径创建一个 uri 对象。需要注意的是，在高版本 Android 上，
由于系统限制直接在 Uri 暴露文件的绝对路径，因此返回的 Uri 会是诸如content://...的形式。

#### 参数

| 参数 | 类型 | 描述 |
| ------ | ------ | ------ |
| `pathOrUri` | `string` | 文件路径，例如"/sdcard/1.txt" |

#### 返回

`Uri`

`Uri` 一个指向该文件的 Uri 的对象，参见[android.net.Uri](https://developer.android.com/reference/android/net/Uri)

***

### launch()

```ts
function launch(packageName): boolean
```

定义于: src/app/index.ts:143

通过应用包名启动应用。如果该包名对应的应用不存在，则返回 false；否则返回 true。

#### 参数

| 参数 | 类型 | 描述 |
| ------ | ------ | ------ |
| `packageName` | `string` | 应用包名 |

#### 返回

`boolean`

***

### launchApp()

```ts
function launchApp(targetAppName): boolean
```

定义于: src/app/index.ts:151

通过应用名称启动应用。如果该名称对应的应用不存在，
则返回 false; 否则返回 true。如果该名称对应多个应用，则只启动其中某一个。

#### 参数

| 参数 | 类型 | 描述 |
| ------ | ------ | ------ |
| `targetAppName` | `string` | 应用名称 |

#### 返回

`boolean`

***

### makeIntent()

```ts
function makeIntent(options): Intent
```

定义于: src/app/index.ts:35

根据选项构造一个Intent

#### 参数

| 参数 | 类型 | 描述 |
| ------ | ------ | ------ |
| `options` | [`IntentOptions`](interfaces/IntentOptions.md) | 选项 |

#### 返回

`Intent`

***

### openAppSettings()

```ts
function openAppSettings(packageName): boolean
```

定义于: src/app/index.ts:158

打开应用的详情页(设置页)。如果找不到该应用，返回 false; 否则返回 true。

#### 参数

| 参数 | 类型 | 描述 |
| ------ | ------ | ------ |
| `packageName` | `string` | 应用包名 |

#### 返回

`boolean`

***

### openUrl()

```ts
function openUrl(url): void
```

定义于: src/app/index.ts:166

用浏览器打开网站 url。
如果没有安装浏览器应用，则抛出ActivityNotException。

#### 参数

| 参数 | 类型 | 描述 |
| ------ | ------ | ------ |
| `url` | `string` | 网站的 Url，如果不以"http://"或"https://"开头则默认是"http://"。 |

#### 返回

`void`

***

### parseUri()

```ts
function parseUri(uri): Uri
```

定义于: src/app/index.ts:178

解析 uri 字符串并返回相应的 Uri 对象。即使 Uri 格式错误，该函数也会返回一个 Uri 对象，
但之后如果访问该对象的 scheme, path 等值可能因解析失败而返回null。

需要注意的是，在高版本 Android 上，由于系统限制直接在 Uri 暴露文件的绝对路径，
因此如果 uri 字符串是文件file://...，返回的 Uri 会是诸如content://...的形式。

#### 参数

| 参数 | 类型 | 描述 |
| ------ | ------ | ------ |
| `uri` | `string` | uri字符串 |

#### 返回

`Uri`

一个指向该文件的 Uri 的对象，参见[android.net.Uri](https://developer.android.com/reference/android/net/Uri)

***

### sendBroadcast()

```ts
function sendBroadcast(options): void
```

定义于: src/app/index.ts:188

根据选项构造一个 Intent，并发送该广播。

#### 参数

| 参数 | 类型 | 描述 |
| ------ | ------ | ------ |
| `options` | [`IntentOptions`](interfaces/IntentOptions.md) | Intent构造选项，参见[makeIntent](README.md#makeintent) |

#### 返回

`void`

***

### sendEmail()

```ts
function sendEmail(options): void
```

定义于: src/app/index.ts:196

根据选项options调用邮箱应用发送邮件。这些选项均是可选的。
如果没有安装邮箱应用，则抛出ActivityNotException。

#### 参数

| 参数 | 类型 | 描述 |
| ------ | ------ | ------ |
| `options` | [`EmailOptions`](interfaces/EmailOptions.md) | 发送邮件的参数 |

#### 返回

`void`

***

### startActivity()

```ts
function startActivity(target): void
```

定义于: src/app/index.ts:225

根据选项构造一个 Intent，并启动该 Activity。

#### 参数

| 参数 | 类型 | 描述 |
| ------ | ------ | ------ |
| `target` | [`IntentOptions`](interfaces/IntentOptions.md) | Intent构造选项，参见[makeIntent](README.md#makeintent) |

#### 返回

`void`

***

### startService()

```ts
function startService(target): void
```

定义于: src/app/index.ts:233

根据选项构造一个 Intent，并启动该服务。

#### 参数

| 参数 | 类型 | 描述 |
| ------ | ------ | ------ |
| `target` | [`IntentOptions`](interfaces/IntentOptions.md) | Intent构造选项，参见[makeIntent](README.md#makeintent) |

#### 返回

`void`

***

### uninstall()

```ts
function uninstall(packageName): void
```

定义于: src/app/index.ts:247

卸载应用。执行后会会弹出卸载应用的提示框。
如果该包名的应用未安装，由应用卸载程序处理，可能弹出"未找到应用"的提示。

#### 参数

| 参数 | 类型 | 描述 |
| ------ | ------ | ------ |
| `packageName` | `string` | 应用包名 |

#### 返回

`void`

#### 示例

```js
import { uninstall } from 'app
//卸载QQ
uninstall("com.tencent.mobileqq");
```

***

### viewFile()

```ts
function viewFile(file): void
```

定义于: src/app/index.ts:87

用其他应用查看文件。文件不存在的情况由查看文件的应用处理。
 如果找不出可以查看该文件的应用，则抛出`ActivityNotException`。

#### 参数

| 参数 | 类型 | 描述 |
| ------ | ------ | ------ |
| `file` | `string` | 文件路径 |

#### 返回

`void`
