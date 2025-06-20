# ModifierExtension

这个对象导出用于设置`Modifier`的函数

## 函数

### background()

```ts
function background(color): ModifierExt
```

定义于: src/vue-ui/modifierExtension.ts:27

设置背景色，传入'theme'表示使用当前主题的背景色,
使用数值表示时必须是bigint，普通数值在java会转成Int溢出范围

#### 参数

| 参数 | 类型 | 描述 |
| ------ | ------ | ------ |
| `color` | `string` \| `bigint` | 颜色值,如`0xffffcc12n`的数值或`#ffcc89`的字符串 |

#### 返回

`ModifierExt`

***

### clickable()

```ts
function clickable(clickable): ModifierExt
```

定义于: src/vue-ui/modifierExtension.ts:101

设置组件可点击，大部分组件会自动添加相应的点击效果

#### 参数

| 参数 | 类型 | 描述 |
| ------ | ------ | ------ |
| `clickable` | () => `void` |  |

#### 返回

`ModifierExt`

***

### fillMaxHeight()

```ts
function fillMaxHeight(): ModifierExt
```

定义于: src/vue-ui/modifierExtension.ts:50

#### 返回

`ModifierExt`

***

### fillMaxSize()

```ts
function fillMaxSize(): ModifierExt
```

定义于: src/vue-ui/modifierExtension.ts:44

#### 返回

`ModifierExt`

***

### fillMaxWidth()

```ts
function fillMaxWidth(): ModifierExt
```

定义于: src/vue-ui/modifierExtension.ts:47

#### 返回

`ModifierExt`

***

### height()

```ts
function height(height): ModifierExt
```

定义于: src/vue-ui/modifierExtension.ts:66

设置高度，单位为dp

#### 参数

| 参数 | 类型 | 描述 |
| ------ | ------ | ------ |
| `height` | `number` |  |

#### 返回

`ModifierExt`

***

### heightIn()

```ts
function heightIn(min?, max?): ModifierExt
```

定义于: src/vue-ui/modifierExtension.ts:133

设置组件最小或最大高度

#### 参数

| 参数 | 类型 | 描述 |
| ------ | ------ | ------ |
| `min`? | `number` | 最小值，可以为null |
| `max`? | `number` | 最大值，可以为null |

#### 返回

`ModifierExt`

***

### horizontalScroll()

```ts
function horizontalScroll(): ModifierExt
```

定义于: src/vue-ui/modifierExtension.ts:108

设置可水平滚动

#### 返回

`ModifierExt`

***

### padding()

```ts
function padding(
   left, 
   top?, 
   right?, 
   bottom?): ModifierExt
```

定义于: src/vue-ui/modifierExtension.ts:86

设置padding，传递一个值时表示四边均使用此值，
传递两个值时第一个参数表示水平padding，第二个参数表示垂直padding

#### 参数

| 参数 | 类型 | 描述 |
| ------ | ------ | ------ |
| `left` | `number` |  |
| `top`? | `number` |  |
| `right`? | `number` |  |
| `bottom`? | `number` |  |

#### 返回

`ModifierExt`

***

### rotate()

```ts
function rotate(angle): ModifierExt
```

定义于: src/vue-ui/modifierExtension.ts:74

设置旋转角度

#### 参数

| 参数 | 类型 | 描述 |
| ------ | ------ | ------ |
| `angle` | `number` | 一般为0~360 |

#### 返回

`ModifierExt`

***

### verticalScroll()

```ts
function verticalScroll(): ModifierExt
```

定义于: src/vue-ui/modifierExtension.ts:115

设置可垂直滚动

#### 返回

`ModifierExt`

***

### weight()

```ts
function weight(i): ModifierExt
```

定义于: src/vue-ui/modifierExtension.ts:37

仅在row和column直接子组件上使用有效,表示此组件占用父组件剩余空间的权重,
假如有一个row剩余高度为300，有一个子组件这设置了此修饰符为1,那么它的高度为300,
如果有两个组件都设为1，那么这两个组件各得150高度

#### 参数

| 参数 | 类型 | 描述 |
| ------ | ------ | ------ |
| `i` | `number` |  |

#### 返回

`ModifierExt`

***

### width()

```ts
function width(width): ModifierExt
```

定义于: src/vue-ui/modifierExtension.ts:58

设置高度，单位为dp

#### 参数

| 参数 | 类型 | 描述 |
| ------ | ------ | ------ |
| `width` | `number` |  |

#### 返回

`ModifierExt`

***

### widthIn()

```ts
function widthIn(min?, max?): ModifierExt
```

定义于: src/vue-ui/modifierExtension.ts:124

设置组件最小或最大宽度

#### 参数

| 参数 | 类型 | 描述 |
| ------ | ------ | ------ |
| `min`? | `number` | 最小值，可以为null |
| `max`? | `number` | 最大值，可以为null |

#### 返回

`ModifierExt`
