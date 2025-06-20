# 类: MediaPlayer

定义于: src/media/index.ts:3

## 构造函数

### new MediaPlayer()

```ts
new MediaPlayer(): MediaPlayer
```

#### 返回

[`MediaPlayer`](MediaPlayer.md)

## 访问器

### androidMediaPlayer

#### Getter 签名

```ts
get androidMediaPlayer(): any
```

定义于: src/media/index.ts:5

##### 返回

`any`

***

### currentPosition

#### Getter 签名

```ts
get currentPosition(): number
```

定义于: src/media/index.ts:11

##### 返回

`number`

当前播放位置。单位毫秒。

***

### duration

#### Getter 签名

```ts
get duration(): number
```

定义于: src/media/index.ts:17

##### 返回

`number`

音乐时长。单位毫秒。

***

### isPlaying

#### Getter 签名

```ts
get isPlaying(): boolean
```

定义于: src/media/index.ts:20

##### 返回

`boolean`

## 方法

### pause()

```ts
pause(): void
```

定义于: src/media/index.ts:30

#### 返回

`void`

***

### play()

```ts
play(
   uri, 
   volume?, 
looping?): Promise<void>
```

定义于: src/media/index.ts:23

#### 参数

| 参数 | 类型 |
| ------ | ------ |
| `uri` | `string` |
| `volume`? | `number` |
| `looping`? | `boolean` |

#### 返回

`Promise`\<`void`\>

***

### prepare()

```ts
prepare(): Promise<void>
```

定义于: src/media/index.ts:33

#### 返回

`Promise`\<`void`\>

***

### prepareSync()

```ts
prepareSync(): void
```

定义于: src/media/index.ts:36

#### 返回

`void`

***

### release()

```ts
release(): void
```

定义于: src/media/index.ts:39

#### 返回

`void`

***

### reset()

```ts
reset(): void
```

定义于: src/media/index.ts:42

#### 返回

`void`

***

### seekTo()

```ts
seekTo(msec): Promise<void>
```

定义于: src/media/index.ts:45

#### 参数

| 参数 | 类型 |
| ------ | ------ |
| `msec` | `number` |

#### 返回

`Promise`\<`void`\>

***

### setDataSource()

```ts
setDataSource(path): void
```

定义于: src/media/index.ts:48

#### 参数

| 参数 | 类型 |
| ------ | ------ |
| `path` | `string` |

#### 返回

`void`

***

### setLooping()

```ts
setLooping(looping): void
```

定义于: src/media/index.ts:51

#### 参数

| 参数 | 类型 |
| ------ | ------ |
| `looping` | `boolean` |

#### 返回

`void`

***

### setScreenOnWhilePlaying()

```ts
setScreenOnWhilePlaying(keep): void
```

定义于: src/media/index.ts:54

#### 参数

| 参数 | 类型 |
| ------ | ------ |
| `keep` | `boolean` |

#### 返回

`void`

***

### setVolume()

```ts
setVolume(leftVolume, rightVolume?): void
```

定义于: src/media/index.ts:57

#### 参数

| 参数 | 类型 |
| ------ | ------ |
| `leftVolume` | `number` |
| `rightVolume`? | `number` |

#### 返回

`void`

***

### start()

```ts
start(): void
```

定义于: src/media/index.ts:61

#### 返回

`void`

***

### stop()

```ts
stop(): void
```

定义于: src/media/index.ts:64

#### 返回

`void`
