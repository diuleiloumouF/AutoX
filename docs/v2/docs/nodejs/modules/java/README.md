# java

该模块用于与java交互

## 变量

### android

```ts
const android: any = Packages.android;
```

定义于: src/java/index.ts:89

***

### androidx

```ts
const androidx: any = Packages.androidx;
```

定义于: src/java/index.ts:93

***

### com

```ts
const com: any = Packages.com;
```

定义于: src/java/index.ts:91

***

### java

```ts
const java: any = Packages.java;
```

定义于: src/java/index.ts:88

***

### javax

```ts
const javax: any = Packages.javax;
```

定义于: src/java/index.ts:90

***

### net

```ts
const net: any = Packages.net;
```

定义于: src/java/index.ts:92

***

### Packages

```ts
const Packages: any;
```

定义于: src/java/index.ts:86

用于向rhino一样访问java类，如
`Packages.java`或`Packages.javax`
此外该模块直接导出了常用的包
```js
import { java, android, com } from 'java'

new java.io.File(...)
```

## 函数

### invokeDefault()

```ts
function invokeDefault<T>(
   javaobj, 
   methodName, 
args?): Promise<T>
```

定义于: src/java/index.ts:35

**`Alpha`**

采用默认的计算线程池异步调用java方法，返回Promise接受结果

#### 类型参数

| 类型参数 |
| ------ |
| `T` |

#### 参数

| 参数 | 类型 | 描述 |
| ------ | ------ | ------ |
| `javaobj` | `any` | java对象，不能是js对象 |
| `methodName` | `string` | 要调用的方法名 |
| `args`? | `any`[] | 传递的参数 |

#### 返回

`Promise`\<`T`\>

调用结果

***

### invokeIo()

```ts
function invokeIo<T>(
   javaobj, 
   methodName, 
args?): Promise<T>
```

定义于: src/java/index.ts:57

**`Alpha`**

和[invokeDefault](README.md#invokedefault)类似，采用io线程池

#### 类型参数

| 类型参数 |
| ------ |
| `T` |

#### 参数

| 参数 | 类型 | 描述 |
| ------ | ------ | ------ |
| `javaobj` | `any` |  |
| `methodName` | `string` |  |
| `args`? | `any`[] |  |

#### 返回

`Promise`\<`T`\>

***

### invokeUi()

```ts
function invokeUi<T>(
   javaobj, 
   methodName, 
args?): Promise<T>
```

定义于: src/java/index.ts:71

**`Alpha`**

和[invokeDefault](README.md#invokedefault)类似，采用ui线程

#### 类型参数

| 类型参数 |
| ------ |
| `T` |

#### 参数

| 参数 | 类型 | 描述 |
| ------ | ------ | ------ |
| `javaobj` | `any` |  |
| `methodName` | `string` |  |
| `args`? | `any`[] |  |

#### 返回

`Promise`\<`T`\>

***

### loadClass()

```ts
function loadClass(className): JavaClass
```

定义于: src/java/index.ts:46

**`Alpha`**

加载并返回一个java类

#### 参数

| 参数 | 类型 | 描述 |
| ------ | ------ | ------ |
| `className` | `string` | java全类名 |

#### 返回

`JavaClass`
