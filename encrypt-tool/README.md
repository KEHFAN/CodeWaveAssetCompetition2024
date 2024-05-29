# encrypt-tool
封装各种加解密算法，方便制品使用。

# 接口详情
## DecryptUtil.decryptWithBase64AndDes
先base64解密,再des解密
入参：

| 属性              | 类型     | 描述          |
|-----------------|--------|-------------|
| key             | String | 密钥          |
| encryptedString | String | base64编码的密文 |
出参：

| 属性 | 类型     | 描述      |
|----|--------|---------|
|    | String | 解密后的字符串 |

## DecryptUtil.decryptWithAESAndBase64
先base64解密，再aes解密

## DecryptUtil.decryptWithMySqlAESAndHex
## DecryptUtil.decryptWithBase64
## DecryptUtil.decryptWithSM2C132
## DecryptUtil.decryptWithSM2C123
## DecryptUtil.decryptWithSM4
## EncryptUtil.encryptWithDes
## EncryptUtil.encryptWithDesAndHex
## EncryptUtil.encryptWithDesAndBase64
## EncryptUtil.encryptWithMD5AndBase64
## EncryptUtil.encryptWithSHA1AndHexLowerCase
## EncryptUtil.encryptWithSM3AndHexLowerCase
## EncryptUtil.encryptWithSHA256AndBase64
## EncryptUtil.encryptWithMD5AndSHA1Base64
## EncryptUtil.encryptWithSHA256AndHexLowerCase
## EncryptUtil.encryptWithSHA256AndGetTopEightToHexLowerCase
## EncryptUtil.encryptWithSHA256ToHexLowerCase
## EncryptUtil.encryptWithAESAndBase64
## EncryptUtil.encryptWithMySqlAESAndHex
## EncryptUtil.encryptWithBase64
## EncryptUtil.encryptWithSM2C132
## EncryptUtil.encryptWithSM2C123
## EncryptUtil.encryptWithSM4
## EncryptUtil.encryptWithSM3Base64
## MD5Util.encryptWithMD5
## RSAUtil.signByPublicKeyWithRSA
## RSAUtil.signByPrivateKeyWithRSA
## UrlEncodeUtil.encryptWithUrlEncode
## UrlEncodeUtil.encryptWithUrlEncodeByEnc
## UrlEncodeUtil.decryptWithUrlDecode
## UrlEncodeUtil.decryptWithUrlDecodeByEnc