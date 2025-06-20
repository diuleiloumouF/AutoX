# dialogs

## 枚举

- [DialogEvent](enumerations/DialogEvent.md)

## 类

- [DialogFactory](classes/DialogFactory.md)

## 接口

- [DialogInterface](interfaces/DialogInterface.md)
- [IDialogs](interfaces/IDialogs.md)

## 类型别名

### DialogType

```ts
type DialogType = "app" | "overlay" | DialogFactory;
```

定义于: src/dialogs/index.ts:14

## 变量

### defaultDialogType

```ts
const defaultDialogType: DialogType = 'app';
```

定义于: src/dialogs/index.ts:15

## 函数

### showAlertDialog()

```ts
function showAlertDialog(title, options?): Promise<void>
```

定义于: src/dialogs/index.ts:106

显示一个消息提示对话框，返回一个Promise

#### 参数

| 参数 | 类型 | 描述 |
| ------ | ------ | ------ |
| `title` | `string` |  |
| `options`? | `DialogBuilderOptions` |  |

#### 返回

`Promise`\<`void`\>

Promise将在对话框消失时完成

***

### showAppDialog()

```ts
function showAppDialog(comp, ops?): AppDialogBuilder
```

定义于: src/dialogs/index.ts:18

#### 参数

| 参数 | 类型 |
| ------ | ------ |
| `comp` | `Component` |
| `ops`? | `DialogOps` |

#### 返回

`AppDialogBuilder`

***

### showConfirmDialog()

```ts
function showConfirmDialog(title, options?): Promise<boolean>
```

定义于: src/dialogs/index.ts:125

显示一个确认对话框

#### 参数

| 参数 | 类型 | 描述 |
| ------ | ------ | ------ |
| `title` | `string` |  |
| `options`? | `DialogBuilderOptions` |  |

#### 返回

`Promise`\<`boolean`\>

只在点击positive按钮时返回true,其他情况返回false

***

### showDialog()

```ts
function showDialog(options): DialogInterface
```

定义于: src/dialogs/index.ts:61

#### 参数

| 参数 | 类型 |
| ------ | ------ |
| `options` | `DialogBuilderOptions` |

#### 返回

[`DialogInterface`](interfaces/DialogInterface.md)

***

### showInputDialog()

```ts
function showInputDialog(
   title, 
   prefill?, 
options?): Promise<null | string>
```

定义于: src/dialogs/index.ts:149

显示一个输入框，提示用户输入信息

#### 参数

| 参数 | 类型 | 描述 |
| ------ | ------ | ------ |
| `title` | `string` |  |
| `prefill`? | `string` | 输入框的默认内容 |
| `options`? | `InputDialogOptions` |  |

#### 返回

`Promise`\<`null` \| `string`\>

点击positive时返回字符串，即使输入为空，被取消时返回null

***

### showMultiChoiceDialog()

```ts
function showMultiChoiceDialog(
   title, 
   items, 
   initialSelectedIndices?, 
options?): Promise<null | number[]>
```

定义于: src/dialogs/index.ts:229

显示一个多选对话框

#### 参数

| 参数 | 类型 | 描述 |
| ------ | ------ | ------ |
| `title` | `string` |  |
| `items` | `string`[] | 可多选的项目 |
| `initialSelectedIndices`? | `number`[] | 初始选中的项目索引数组 |
| `options`? | `DialogBuilderOptions` |  |

#### 返回

`Promise`\<`null` \| `number`[]\>

返回选中的项目索引数组，被取消则返回`null`

***

### showSelectDialog()

```ts
function showSelectDialog(
   title, 
   items, 
options?): Promise<number>
```

定义于: src/dialogs/index.ts:190

显示一个选择对话框，选中任意项后消失

#### 参数

| 参数 | 类型 | 描述 |
| ------ | ------ | ------ |
| `title` | `string` |  |
| `items` | `string`[] | 选项数组 |
| `options`? | `DialogBuilderOptions` |  |

#### 返回

`Promise`\<`number`\>

返回选中的项目索引，被取消则返回-1

***

### showSingleChoiceDialog()

```ts
function showSingleChoiceDialog(
   title, 
   items, 
   initialSelectedIndex?, 
options?): Promise<number>
```

定义于: src/dialogs/index.ts:290

显示一个单选对话框

#### 参数

| 参数 | 类型 | 描述 |
| ------ | ------ | ------ |
| `title` | `string` |  |
| `items` | `string`[] |  |
| `initialSelectedIndex`? | `number` |  |
| `options`? | `DialogBuilderOptions` |  |

#### 返回

`Promise`\<`number`\>

返回选中的项目索引，被取消则返回-1
