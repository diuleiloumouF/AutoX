# 接口: EmailOptions

定义于: src/app/types.ts:14

## 属性

### attachment?

```ts
optional attachment: string;
```

定义于: src/app/types.ts:16

附件的路径。

***

### bcc?

```ts
optional bcc: string | string[];
```

定义于: src/app/types.ts:18

密送收件人的邮件地址。如果有多个密送收件人，则用字符串数组表示

***

### cc?

```ts
optional cc: string | string[];
```

定义于: src/app/types.ts:20

抄送收件人的邮件地址。如果有多个抄送收件人，则用字符串数组表示

***

### email?

```ts
optional email: string | string[];
```

定义于: src/app/types.ts:22

收件人的邮件地址。如果有多个收件人，则用字符串数组表示

***

### subject?

```ts
optional subject: string;
```

定义于: src/app/types.ts:24

邮件主题(标题)

***

### text?

```ts
optional text: string;
```

定义于: src/app/types.ts:26

邮件正文
