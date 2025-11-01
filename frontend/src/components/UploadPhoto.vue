<template>
  <el-upload class="avatar-uploader"
              action="string"
              :show-file-list="false"
              :on-change="handleAvatarChange"
              :http-request="uploadProfile">
    <img v-if="avatar" :src="avatar" class="avatar" />
    <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
  </el-upload>
</template>
  
<script setup>
import { getCurrentInstance, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { Plus } from '@element-plus/icons-vue'
import { useuserStore } from '../stores/user'

const user = useuserStore()
const { proxy } = getCurrentInstance()
const acceptType = ['image/bmp', 'image/gif', 'image/jpg', 'image/jpeg', 'image/png']

const avatar = ref(user.head)
const avatarFile = ref(null)
let handleAvatarPromise = null;

const readImg = function (file) {
  return new Promise((resolve, reject) => {
    const img = new Image();
    const reader = new FileReader();
    reader.onload = function (e) {
      img.src = e.target.result;
    };
    reader.onerror = function (e) {
      reject(e);
    };
    reader.readAsDataURL(file);
    img.onload = function () {
      resolve(img);
    };
    img.onerror = function (e) {
      reject(e);
    };
  });
};

const handleAvatarChange = async (file) => {
  handleAvatarPromise = new Promise(async (resolve, reject) => {
    if (!acceptType.includes(file.raw.type)) {
      ElMessage.error('上传文件格式务必 PNG|JPG')
      return false
    } else if (file.size / 1024 / 1024 > 4) {
      ElMessage.error('上传文件大小不能超过 4M')
      return false
    }

    const img = await readImg(file.raw);
    const canvas = document.createElement("canvas");
    const context = canvas.getContext("2d");
    const { width: originWidth, height: originHeight } = img;
    // 最大尺寸限制
    const maxWidth = 200;
    const maxHeight = 200;
    // 目标尺寸
    let targetWidth = originWidth;
    let targetHeight = originHeight;
    if (originWidth > maxWidth || originHeight > maxHeight) {
      if (originWidth / originHeight > 1) {
        // 宽图片
        targetWidth = maxWidth;
        targetHeight = Math.round(maxWidth * (originHeight / originWidth));
      } else {
        // 高图片
        targetHeight = maxHeight;
        targetWidth = Math.round(maxHeight * (originWidth / originHeight));
      }
    }
    canvas.width = targetWidth;
    canvas.height = targetHeight;
    if (context) {
      context.clearRect(0, 0, targetWidth, targetHeight);
      // 图片绘制
      context.drawImage(img, 0, 0, targetWidth, targetHeight);
    }
    
    const compressedFile = await new Promise(resolve => canvas.toBlob(resolve, file.raw.type));

    avatarFile.value = compressedFile;
    avatar.value = URL.createObjectURL(compressedFile);
    
    console.log(avatarFile.value)

    resolve(true)
  })

  return handleAvatarPromise
}

const uploadProfile = async () => {
  if (handleAvatarPromise) {
    await handleAvatarPromise;
  }
  const reader = new FileReader()
  reader.onload = (event) => {
    const arrayBuffer = event.target.result;
    const byteArray = new Uint8Array(arrayBuffer);
    const numberArray = Array.from(byteArray);
    console.log("Length of profile: ", numberArray.length)
    const profileRequest = {
      jAccount: user.jAccount,
      profile: numberArray
    }
    const config = {
      'Content-Type': 'application/json'
    }
    return proxy.$axios.post('/api/uploadProfile', profileRequest, config)
  }
  reader.readAsArrayBuffer(avatarFile.value)
  user.updateHead(URL.createObjectURL(avatarFile.value))
  ElMessage.success('上传头像成功')
}
</script>

<style scoped>
.avatar-uploader .avatar {
  width: 178px;
  height: 178px;
  display: block;
}
.avatar-uploader .el-upload {
  border: 1px dashed var(--el-border-color);
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: var(--el-transition-duration-fast);
}

.avatar-uploader .el-upload:hover {
  border-color: var(--el-color-primary);
}

.el-icon.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  text-align: center;
}
</style>