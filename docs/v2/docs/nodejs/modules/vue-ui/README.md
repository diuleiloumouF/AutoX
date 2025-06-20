# vue-ui

入门

## 文档

- [README](documents/README.md)

## 命名空间

- [Icons](namespaces/Icons/README.md)
- [ModifierExtension](namespaces/ModifierExtension/README.md)
- [Theme](namespaces/Theme/README.md)

## 接口

- [ActivityEventListener](interfaces/ActivityEventListener.md)

## 函数

### createApp()

```ts
function createApp(rootComponent, rootProps?): App<PxElement>
```

定义于: src/vue-ui/index.ts:28

#### 参数

| 参数 | 类型 |
| ------ | ------ |
| `rootComponent` | `Component` |
| `rootProps`? | `null` \| `Data` |

#### 返回

`App`\<`PxElement`\>

***

### render()

```ts
function render(
   vnode, 
   container, 
   namespace?): void
```

定义于: src/vue-ui/index.ts:27

#### 参数

| 参数 | 类型 |
| ------ | ------ |
| `vnode` | `null` \| `VNode`\<`RendererNode`, `RendererElement`, \{\}\> |
| `container` | `PxElement` |
| `namespace`? | `ElementNamespace` |

#### 返回

`void`

***

### renderActivity()

```ts
function renderActivity(vnode, listener?): void
```

定义于: src/vue-ui/index.ts:48

#### 参数

| 参数 | 类型 |
| ------ | ------ |
| `vnode` | `VNode` |
| `listener`? | [`ActivityEventListener`](interfaces/ActivityEventListener.md) |

#### 返回

`void`

***

### setDebug()

```ts
function setDebug(d): void
```

定义于: src/vue-ui/nodeOps.ts:88

#### 参数

| 参数 | 类型 |
| ------ | ------ |
| `d` | `boolean` |

#### 返回

`void`

***

### startActivity()

```ts
function startActivity(app, listener?): Promise<Activity>
```

定义于: src/vue-ui/index.ts:69

启动Activity并挂载app实例作为内容

#### 参数

| 参数 | 类型 | 描述 |
| ------ | ------ | ------ |
| `app` | `App`\<`PxElement`\> | Vue的app实例 |
| `listener`? | [`ActivityEventListener`](interfaces/ActivityEventListener.md) | 用于监听该Activity各种事件的监听器 |

#### 返回

`Promise`\<`Activity`\>

当Activity创建完成后返回该Activity实例

***

### xml()

```ts
function xml(strings, ...values): 
  | VNode<RendererNode, RendererElement, {}>
  | VNode<RendererNode, RendererElement, {}>[]
```

定义于: src/vue-ui/index.ts:82

该函数用于创建VNode节点，详细参考[htm](https://github.com/developit/htm)

#### 参数

| 参数 | 类型 |
| ------ | ------ |
| `strings` | `TemplateStringsArray` |
| ...`values` | `any`[] |

#### 返回

  \| `VNode`\<`RendererNode`, `RendererElement`, \{\}\>
  \| `VNode`\<`RendererNode`, `RendererElement`, \{\}\>[]
