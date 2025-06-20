# accessibility

该模块提供基于无障碍相关方法，使用方法与旧版api略有不同，

## 示例

```js
import { click } from 'accessibility';

await click(100, 100)
```

## 接口

- [GestureOp](interfaces/GestureOp.md)
- [Point](interfaces/Point.md)

## 函数

### back()

```ts
function back(): Promise<boolean>
```

定义于: src/accessibility/index.ts:28

#### 返回

`Promise`\<`boolean`\>

***

### click()

```ts
function click(x, y): Promise<boolean>
```

定义于: src/accessibility/index.ts:32

#### 参数

| 参数 | 类型 |
| ------ | ------ |
| `x` | `number` |
| `y` | `number` |

#### 返回

`Promise`\<`boolean`\>

***

### clickText()

```ts
function clickText(text, index): Promise<boolean>
```

定义于: src/accessibility/index.ts:35

#### 参数

| 参数 | 类型 | 默认值 |
| ------ | ------ | ------ |
| `text` | `string` | `undefined` |
| `index` | `number` | `0` |

#### 返回

`Promise`\<`boolean`\>

***

### currentActivity()

```ts
function currentActivity(): null | string
```

定义于: src/accessibility/index.ts:39

#### 返回

`null` \| `string`

***

### currentPackage()

```ts
function currentPackage(): null | string
```

定义于: src/accessibility/index.ts:42

#### 返回

`null` \| `string`

***

### home()

```ts
function home(): Promise<boolean>
```

定义于: src/accessibility/index.ts:45

#### 返回

`Promise`\<`boolean`\>

***

### inputText()

```ts
function inputText(text, index?): Promise<boolean>
```

定义于: src/accessibility/index.ts:48

#### 参数

| 参数 | 类型 |
| ------ | ------ |
| `text` | `string` |
| `index`? | `number` |

#### 返回

`Promise`\<`boolean`\>

***

### lockScreen()

```ts
function lockScreen(): Promise<boolean>
```

定义于: src/accessibility/index.ts:52

#### 返回

`Promise`\<`boolean`\>

***

### longClick()

```ts
function longClick(x, y): Promise<boolean>
```

定义于: src/accessibility/index.ts:55

#### 参数

| 参数 | 类型 |
| ------ | ------ |
| `x` | `number` |
| `y` | `number` |

#### 返回

`Promise`\<`boolean`\>

***

### openNotifications()

```ts
function openNotifications(): Promise<boolean>
```

定义于: src/accessibility/index.ts:59

#### 返回

`Promise`\<`boolean`\>

***

### openQuickSettings()

```ts
function openQuickSettings(): Promise<boolean>
```

定义于: src/accessibility/index.ts:62

#### 返回

`Promise`\<`boolean`\>

***

### performGesture()

```ts
function performGesture(
   points, 
   duration, 
delay): Promise<boolean>
```

定义于: src/accessibility/index.ts:65

#### 参数

| 参数 | 类型 | 默认值 |
| ------ | ------ | ------ |
| `points` | [`Point`](interfaces/Point.md)[] | `undefined` |
| `duration` | `number` | `undefined` |
| `delay` | `number` | `0` |

#### 返回

`Promise`\<`boolean`\>

***

### performGestures()

```ts
function performGestures(gestures): Promise<boolean>
```

定义于: src/accessibility/index.ts:68

#### 参数

| 参数 | 类型 |
| ------ | ------ |
| `gestures` | [`GestureOp`](interfaces/GestureOp.md)[] |

#### 返回

`Promise`\<`boolean`\>

***

### performGlobalAction()

```ts
function performGlobalAction(): void
```

定义于: src/accessibility/index.ts:84

#### 返回

`void`

***

### press()

```ts
function press(
   x, 
   y, 
duration): Promise<boolean>
```

定义于: src/accessibility/index.ts:87

#### 参数

| 参数 | 类型 |
| ------ | ------ |
| `x` | `number` |
| `y` | `number` |
| `duration` | `number` |

#### 返回

`Promise`\<`boolean`\>

***

### select()

```ts
function select(): UiSelector
```

定义于: src/accessibility/index.ts:102

返回一个ui选择器，选择器的阻塞方法目前会阻塞nodejs事件循环，
请通过java模块相关方法解决

#### 返回

`UiSelector`

***

### setText()

```ts
function setText(text, index): Promise<boolean>
```

定义于: src/accessibility/index.ts:106

#### 参数

| 参数 | 类型 | 默认值 |
| ------ | ------ | ------ |
| `text` | `string` | `undefined` |
| `index` | `number` | `0` |

#### 返回

`Promise`\<`boolean`\>

***

### swipe()

```ts
function swipe(
   x1, 
   y1, 
   x2, 
   y2, 
duration): Promise<boolean>
```

定义于: src/accessibility/index.ts:111

#### 参数

| 参数 | 类型 |
| ------ | ------ |
| `x1` | `number` |
| `y1` | `number` |
| `x2` | `number` |
| `y2` | `number` |
| `duration` | `number` |

#### 返回

`Promise`\<`boolean`\>

***

### takeScreenshot()

```ts
function takeScreenshot(): Promise<Image>
```

定义于: src/accessibility/index.ts:115

#### 返回

`Promise`\<`Image`\>

***

### togglePowerDialog()

```ts
function togglePowerDialog(): Promise<boolean>
```

定义于: src/accessibility/index.ts:121

显示电源设置。

#### 返回

`Promise`\<`boolean`\>

***

### toggleRecents()

```ts
function toggleRecents(): Promise<boolean>
```

定义于: src/accessibility/index.ts:127

模拟最近任务键

#### 返回

`Promise`\<`boolean`\>
