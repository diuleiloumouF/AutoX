# 接口: ExecutionConfigOptions

定义于: src/engines/index.ts:46

## 属性

### arguments?

```ts
optional arguments: Map<string, any>;
```

定义于: src/engines/index.ts:48

***

### onException()?

```ts
optional onException: (execution, err) => void;
```

定义于: src/engines/index.ts:51

#### 参数

| 参数 | 类型 |
| ------ | ------ |
| `execution` | `ScriptExecution` |
| `err` | `any` |

#### 返回

`void`

***

### onStart()?

```ts
optional onStart: (execution) => void;
```

定义于: src/engines/index.ts:49

#### 参数

| 参数 | 类型 |
| ------ | ------ |
| `execution` | `ScriptExecution` |

#### 返回

`void`

***

### onSuccess()?

```ts
optional onSuccess: (execution, result) => void;
```

定义于: src/engines/index.ts:50

#### 参数

| 参数 | 类型 |
| ------ | ------ |
| `execution` | `ScriptExecution` |
| `result` | `any` |

#### 返回

`void`

***

### workingDirectory?

```ts
optional workingDirectory: string;
```

定义于: src/engines/index.ts:47
