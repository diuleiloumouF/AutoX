# media

## 类

- [MediaPlayer](classes/MediaPlayer.md)

## 函数

### playMusic()

```ts
function playMusic(
   uri, 
   volume?, 
looping?): Promise<MediaPlayer>
```

定义于: src/media/index.ts:69

#### 参数

| 参数 | 类型 |
| ------ | ------ |
| `uri` | `string` |
| `volume`? | `number` |
| `looping`? | `boolean` |

#### 返回

`Promise`\<[`MediaPlayer`](classes/MediaPlayer.md)\>

***

### scanFile()

```ts
function scanFile(file): void
```

定义于: src/media/index.ts:75

#### 参数

| 参数 | 类型 |
| ------ | ------ |
| `file` | `string` |

#### 返回

`void`
