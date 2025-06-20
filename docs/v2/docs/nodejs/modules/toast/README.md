# toast

## 函数

### showToast()

```ts
function showToast(message, option?): void
```

定义于: src/toast/index.ts:14

弹出一条toast

#### 参数

| 参数 | 类型 | 描述 |
| ------ | ------ | ------ |
| `message` | `any` | 要显示的消息 |
| `option`? | `string` \| `ToastOptions` | 可以是`"short" | "long"`，表示弹出时长 |

#### 返回

`void`

#### 示例

```ts
import { showToast } from 'toast'
showToast('hello world')
```
