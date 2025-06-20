# 类: ScriptEngineProxy

定义于: src/engines/index.ts:5

## 继承

- `EventEmitter`

## 构造函数

### new ScriptEngineProxy()

```ts
new ScriptEngineProxy(engine): ScriptEngineProxy
```

定义于: src/engines/index.ts:14

#### 参数

| 参数 | 类型 |
| ------ | ------ |
| `engine` | `ScriptEngine` |

#### 返回

[`ScriptEngineProxy`](ScriptEngineProxy.md)

#### 重写了

```ts
EventEmitter.constructor
```

## 访问器

### id

#### Getter 签名

```ts
get id(): number
```

定义于: src/engines/index.ts:7

##### 返回

`number`

***

### isDestroyed

#### Getter 签名

```ts
get isDestroyed(): boolean
```

定义于: src/engines/index.ts:10

##### 返回

`boolean`

## 方法

### cwd()

```ts
cwd(): string
```

定义于: src/engines/index.ts:32

#### 返回

`string`

***

### emit()

```ts
emit<K>(eventName, ...args): boolean
```

定义于: src/engines/index.ts:18

Synchronously calls each of the listeners registered for the event named `eventName`, in the order they were registered, passing the supplied arguments
to each.

Returns `true` if the event had listeners, `false` otherwise.

```js
import { EventEmitter } from 'node:events';
const myEmitter = new EventEmitter();

// First listener
myEmitter.on('event', function firstListener() {
  console.log('Helloooo! first listener');
});
// Second listener
myEmitter.on('event', function secondListener(arg1, arg2) {
  console.log(`event with parameters ${arg1}, ${arg2} in second listener`);
});
// Third listener
myEmitter.on('event', function thirdListener(...args) {
  const parameters = args.join(', ');
  console.log(`event with parameters ${parameters} in third listener`);
});

console.log(myEmitter.listeners('event'));

myEmitter.emit('event', 1, 2, 3, 4, 5);

// Prints:
// [
//   [Function: firstListener],
//   [Function: secondListener],
//   [Function: thirdListener]
// ]
// Helloooo! first listener
// event with parameters 1, 2 in second listener
// event with parameters 1, 2, 3, 4, 5 in third listener
```

#### 类型参数

| 类型参数 |
| ------ |
| `K` |

#### 参数

| 参数 | 类型 |
| ------ | ------ |
| `eventName` | `string` \| `symbol` |
| ...`args` | `any`[] |

#### 返回

`boolean`

#### 添加于

v0.1.26

#### 重写了

```ts
EventEmitter.emit
```

***

### forceStop()

```ts
forceStop(): void
```

定义于: src/engines/index.ts:25

#### 返回

`void`
