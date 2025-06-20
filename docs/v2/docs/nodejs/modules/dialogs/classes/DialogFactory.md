# 类: DialogFactory

定义于: src/dialogs/DialogFactory.ts:24

## 实现

- [`IDialogs`](../interfaces/IDialogs.md)

## 构造函数

### new DialogFactory()

```ts
new DialogFactory(): DialogFactory
```

#### 返回

[`DialogFactory`](DialogFactory.md)

## 属性

### showin

```ts
showin: ShallowReactive<Set<[Component, DialogStatus]>>;
```

定义于: src/dialogs/DialogFactory.ts:25

## 访问器

### Dialog

#### Getter 签名

```ts
get Dialog(): Component
```

定义于: src/dialogs/DialogFactory.ts:27

##### 返回

`Component`

## 方法

### \_mountUi()

```ts
_mountUi(comp, ops): DialogInterface
```

定义于: src/dialogs/DialogFactory.ts:54

#### 参数

| 参数 | 类型 |
| ------ | ------ |
| `comp` | `Component` |
| `ops` | `DialogOps` |

#### 返回

[`DialogInterface`](../interfaces/DialogInterface.md)

***

### showAlertDialog()

```ts
showAlertDialog(title, options): Promise<void>
```

定义于: src/dialogs/DialogFactory.ts:75

#### 参数

| 参数 | 类型 |
| ------ | ------ |
| `title` | `string` |
| `options` | `DialogBuilderOptions` |

#### 返回

`Promise`\<`void`\>

#### 实现了

[`IDialogs`](../interfaces/IDialogs.md).[`showAlertDialog`](../interfaces/IDialogs.md#showalertdialog)

***

### showConfirmDialog()

```ts
showConfirmDialog(title, options): Promise<boolean>
```

定义于: src/dialogs/DialogFactory.ts:81

#### 参数

| 参数 | 类型 |
| ------ | ------ |
| `title` | `string` |
| `options` | `DialogBuilderOptions` |

#### 返回

`Promise`\<`boolean`\>

#### 实现了

[`IDialogs`](../interfaces/IDialogs.md).[`showConfirmDialog`](../interfaces/IDialogs.md#showconfirmdialog)

***

### showDialog()

```ts
showDialog(options): DialogInterface
```

定义于: src/dialogs/DialogFactory.ts:68

#### 参数

| 参数 | 类型 |
| ------ | ------ |
| `options` | `DialogBuilderOptions` |

#### 返回

[`DialogInterface`](../interfaces/DialogInterface.md)

#### 实现了

[`IDialogs`](../interfaces/IDialogs.md).[`showDialog`](../interfaces/IDialogs.md#showdialog)

***

### showInputDialog()

```ts
showInputDialog(
   title, 
   prefill?, 
options?): Promise<null | string>
```

定义于: src/dialogs/DialogFactory.ts:87

#### 参数

| 参数 | 类型 |
| ------ | ------ |
| `title` | `string` |
| `prefill`? | `string` |
| `options`? | `InputDialogOptions` |

#### 返回

`Promise`\<`null` \| `string`\>

#### 实现了

[`IDialogs`](../interfaces/IDialogs.md).[`showInputDialog`](../interfaces/IDialogs.md#showinputdialog)

***

### showMultiChoiceDialog()

```ts
showMultiChoiceDialog(
   title, 
   items, 
   initialSelectedIndices?, 
options?): Promise<null | number[]>
```

定义于: src/dialogs/DialogFactory.ts:99

#### 参数

| 参数 | 类型 |
| ------ | ------ |
| `title` | `string` |
| `items` | `string`[] |
| `initialSelectedIndices`? | `number`[] |
| `options`? | `DialogBuilderOptions` |

#### 返回

`Promise`\<`null` \| `number`[]\>

#### 实现了

[`IDialogs`](../interfaces/IDialogs.md).[`showMultiChoiceDialog`](../interfaces/IDialogs.md#showmultichoicedialog)

***

### showSelectDialog()

```ts
showSelectDialog(
   title, 
   items, 
options?): Promise<number>
```

定义于: src/dialogs/DialogFactory.ts:93

#### 参数

| 参数 | 类型 |
| ------ | ------ |
| `title` | `string` |
| `items` | `string`[] |
| `options`? | `DialogBuilderOptions` |

#### 返回

`Promise`\<`number`\>

#### 实现了

[`IDialogs`](../interfaces/IDialogs.md).[`showSelectDialog`](../interfaces/IDialogs.md#showselectdialog)

***

### showSingleChoiceDialog()

```ts
showSingleChoiceDialog(
   title, 
   items, 
   initialSelectedIndex?, 
options?): Promise<number>
```

定义于: src/dialogs/DialogFactory.ts:105

#### 参数

| 参数 | 类型 |
| ------ | ------ |
| `title` | `string` |
| `items` | `string`[] |
| `initialSelectedIndex`? | `number` |
| `options`? | `DialogBuilderOptions` |

#### 返回

`Promise`\<`number`\>

#### 实现了

[`IDialogs`](../interfaces/IDialogs.md).[`showSingleChoiceDialog`](../interfaces/IDialogs.md#showsinglechoicedialog)
