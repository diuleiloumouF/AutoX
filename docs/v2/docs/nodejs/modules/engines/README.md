# engines

## 类

- [ScriptEngineProxy](classes/ScriptEngineProxy.md)

## 接口

- [ExecutionConfigOptions](interfaces/ExecutionConfigOptions.md)

## 变量

### selfEngine

```ts
const selfEngine: ScriptEngineProxy;
```

定义于: src/engines/index.ts:40

当前运行的引擎

## 函数

### broadcast()

```ts
function broadcast(event, ...args): void
```

定义于: src/engines/index.ts:117

向所有运行中的脚本发送事件，相当于
```js
getRunningEngines().forEach((engine) => {
       engine.emit(event, ...args)
   })
```

#### 参数

| 参数 | 类型 | 描述 |
| ------ | ------ | ------ |
| `event` | `string` |  |
| ...`args` | `any` |  |

#### 返回

`void`

***

### execScriptFile()

```ts
function execScriptFile(path, ops): ScriptExecution
```

定义于: src/engines/index.ts:66

运行一个脚本文件

#### 参数

| 参数 | 类型 | 描述 |
| ------ | ------ | ------ |
| `path` | `string` | 只能是绝对路径，不支持相对路径 |
| `ops` | [`ExecutionConfigOptions`](interfaces/ExecutionConfigOptions.md) |  |

#### 返回

`ScriptExecution`

***

### getRunningEngines()

```ts
function getRunningEngines(): ScriptEngineProxy[]
```

定义于: src/engines/index.ts:100

获取所有运行中的脚本

#### 返回

[`ScriptEngineProxy`](classes/ScriptEngineProxy.md)[]

***

### myEngine()

```ts
function myEngine(): ScriptEngineProxy
```

定义于: src/engines/index.ts:57

获取当前运行的引擎

#### 返回

[`ScriptEngineProxy`](classes/ScriptEngineProxy.md)

***

### stopAll()

```ts
function stopAll(): void
```

定义于: src/engines/index.ts:93

停止所有运行中的脚本，包括自身

#### 返回

`void`
