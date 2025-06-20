# 接口: IDialogs

定义于: src/dialogs/options.ts:101

## 方法

### showAlertDialog()

```ts
showAlertDialog(title, options?): Promise<void>
```

定义于: src/dialogs/options.ts:103

#### 参数

| 参数 | 类型 |
| ------ | ------ |
| `title` | `string` |
| `options`? | `DialogBuilderOptions` |

#### 返回

`Promise`\<`void`\>

***

### showConfirmDialog()

```ts
showConfirmDialog(title, options?): Promise<boolean>
```

定义于: src/dialogs/options.ts:104

#### 参数

| 参数 | 类型 |
| ------ | ------ |
| `title` | `string` |
| `options`? | `DialogBuilderOptions` |

#### 返回

`Promise`\<`boolean`\>

***

### showDialog()

```ts
showDialog(options): DialogInterface
```

定义于: src/dialogs/options.ts:102

#### 参数

| 参数 | 类型 |
| ------ | ------ |
| `options` | `DialogBuilderOptions` |

#### 返回

[`DialogInterface`](DialogInterface.md)

***

### showInputDialog()

```ts
showInputDialog(
   title, 
   prefill?, 
options?): Promise<null | string>
```

定义于: src/dialogs/options.ts:105

#### 参数

| 参数 | 类型 |
| ------ | ------ |
| `title` | `string` |
| `prefill`? | `string` |
| `options`? | `InputDialogOptions` |

#### 返回

`Promise`\<`null` \| `string`\>

***

### showMultiChoiceDialog()

```ts
showMultiChoiceDialog(
   title, 
   items, 
   initialSelectedIndices?, 
options?): Promise<null | number[]>
```

定义于: src/dialogs/options.ts:107

#### 参数

| 参数 | 类型 |
| ------ | ------ |
| `title` | `string` |
| `items` | `string`[] |
| `initialSelectedIndices`? | `number`[] |
| `options`? | `DialogBuilderOptions` |

#### 返回

`Promise`\<`null` \| `number`[]\>

***

### showSelectDialog()

```ts
showSelectDialog(
   title, 
   items, 
options?): Promise<number>
```

定义于: src/dialogs/options.ts:106

#### 参数

| 参数 | 类型 |
| ------ | ------ |
| `title` | `string` |
| `items` | `string`[] |
| `options`? | `DialogBuilderOptions` |

#### 返回

`Promise`\<`number`\>

***

### showSingleChoiceDialog()

```ts
showSingleChoiceDialog(
   title, 
   items, 
   initialSelectedIndex?, 
options?): Promise<number>
```

定义于: src/dialogs/options.ts:111

#### 参数

| 参数 | 类型 |
| ------ | ------ |
| `title` | `string` |
| `items` | `string`[] |
| `initialSelectedIndex`? | `number` |
| `options`? | `DialogBuilderOptions` |

#### 返回

`Promise`\<`number`\>
