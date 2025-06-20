# clip\_manager

## 变量

### clipboardManager

```ts
const clipboardManager: EventEmitter<DefaultEventMap>;
```

定义于: src/clip\_manager/index.ts:17

此对象是一个EventEmitter，用于监听剪贴板变化

#### 类型参数

```js
clipboardManager.on('clip_changed',()=>{
     getClip()
})
```

## 函数

### clearClip()

```ts
function clearClip(): void
```

定义于: src/clip\_manager/index.ts:36

清空剪贴板

#### 返回

`void`

***

### getClip()

```ts
function getClip(): string
```

定义于: src/clip\_manager/index.ts:28

获取剪贴板内容，需要应用在前台才有效

#### 返回

`string`

***

### hasClip()

```ts
function hasClip(): boolean
```

定义于: src/clip\_manager/index.ts:32

判断剪贴板是否有内容

#### 返回

`boolean`

***

### setClip()

```ts
function setClip(text): void
```

定义于: src/clip\_manager/index.ts:24

设置剪贴板内容

#### 参数

| 参数 | 类型 |
| ------ | ------ |
| `text` | `string` |

#### 返回

`void`
