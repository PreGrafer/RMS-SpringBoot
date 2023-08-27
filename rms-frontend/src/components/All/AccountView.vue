<script setup>
import {reactive, ref} from 'vue';
import {ElForm, ElFormItem, ElInput, ElButton, ElMessage} from 'element-plus';
import {useRoute} from "vue-router";
import {getDataByParam, postByJson} from "@/net";
import {Avatar, Link, Message, User} from "@element-plus/icons-vue";

const route = useRoute();
const userInfoForm = reactive({
  username: '',
  phone: '',
  personid: '',
  name: '',
  gender: '',
  age: '',
  health_status: '',
  education_background: '',
  work_experience: '',
  old_house: '',
  notes: ''
});

const genderOptions = [
  {value: '男', label: '男'},
  {value: '女', label: '女'},
  {value: '其它', label: '其它'}
]

const fetchUserData = () => {
  getDataByParam('/api/info/get-info', {username: route.params.username}, (data) => {
    userInfoForm.username = data.data.username;
    userInfoForm.phone = data.data.phone;
    userInfoForm.personid = data.data.personid;
    userInfoForm.name = data.data.name;
    userInfoForm.gender = data.data.gender;
    userInfoForm.age = data.data.age;
    userInfoForm.health_status = data.data.health_status;
    userInfoForm.education_background = data.data.education_background;
    userInfoForm.work_experience = data.data.work_experience;
    userInfoForm.old_house = data.data.old_house;
    userInfoForm.notes = data.data.notes;
  })
}

const rules = {
  age: [
    {required: true, message: '年龄不能为空', trigger: 'blur'},
    {validator: validateAge, trigger: 'blur'}
  ],
  phone: [
    {required: true, message: '电话号码不能为空', trigger: 'blur'},
    {pattern: /^[0-9]{11}$/, message: '请输入有效的11位电话号码', trigger: ['blur']},
  ]
}

function validateAge(rule, value, callback) {
  if (value < 18) {
    callback(new Error('大于等于18'));
  } else {
    callback();
  }
}

const userInfoFormRef = ref()
const submit = () => {
  userInfoFormRef.value.validate((isValid) => {
    if (isValid) {
      postByJson('/api/info/update-info', userInfoForm, (message) => {
        ElMessage.success(message);
      })
    } else {
      ElMessage.warning("请正确填写信息!")
    }
  })
};

fetchUserData();

</script>

<template>
  <div style="margin-left: 15%">
    <el-form ref="userInfoFormRef" :model="userInfoForm"
             :rules="rules" label-width="75px">
      <el-row>
        <el-form-item label="用户名" style="width: 18%">
          <el-input :value="userInfoForm.username" readonly type="text">
            <template #prefix>
              <el-icon>
                <User/>
              </el-icon>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item label="身份证" style="width: 23%">
          <el-input :value="userInfoForm.personid" readonly type="text">
            <template #prefix>
              <el-icon>
                <Link/>
              </el-icon>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item label="电话" prop="phone" style="width: 20%">
          <el-input v-model="userInfoForm.phone" :value="userInfoForm.phone" type="text">
            <template #prefix>
              <el-icon>
                <Message/>
              </el-icon>
            </template>
          </el-input>
        </el-form-item>
      </el-row>
      <el-row>
        <el-form-item label="姓名" style="width: 20%">
          <el-input v-model="userInfoForm.name" :value="userInfoForm.name" type="text">
            <template #prefix>
              <el-icon>
                <Avatar/>
              </el-icon>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item label="性别" style="width: 15.5%">
          <el-select v-model="userInfoForm.gender" :value="userInfoForm.gender" placeholder="选择性别"
                     style="width: 100%">
            <el-option
                v-for="item in genderOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value"
            />
            <template #prefix>
              <el-icon>
                <Avatar/>
              </el-icon>
            </template>
          </el-select>
        </el-form-item>
        <el-form-item label="年龄" prop="age" style="width: 15%">
          <el-input v-model="userInfoForm.age" :value="userInfoForm.age" type="number">
            <template #prefix>
              <el-icon>
                <Avatar/>
              </el-icon>
            </template>
          </el-input>
        </el-form-item>
      </el-row>
      <el-form-item label="健康状况" style="width: 65%">
        <el-input v-model="userInfoForm.health_status" :autosize="{ minRows: 2, maxRows: 4 }"
                  :value="userInfoForm.health_status"
                  size="large"
                  type="textarea">
          <template #prefix>
            <el-icon>
              <Avatar/>
            </el-icon>
          </template>
        </el-input>
      </el-form-item>
      <el-form-item label="教育背景" style="width: 65%">
        <el-input v-model="userInfoForm.education_background" :autosize="{ minRows: 2, maxRows: 4}"
                  :value="userInfoForm.education_background"
                  size="large"
                  type="textarea">
          <template #prefix>
            <el-icon>
              <Avatar/>
            </el-icon>
          </template>
        </el-input>
      </el-form-item>
      <el-form-item label="工作经历" style="width: 65%">
        <el-input v-model="userInfoForm.work_experience"
                  :autosize="{ minRows: 2, maxRows: 4 }" :value="userInfoForm.work_experience" size="large"
                  type="textarea">
          <template #prefix>
            <el-icon>
              <Avatar/>
            </el-icon>
          </template>
        </el-input>
      </el-form-item>
      <el-form-item label="搬迁来源" style="width: 65%">
        <el-input v-model="userInfoForm.old_house" :autosize="{ minRows: 2, maxRows: 4 }"
                  :value="userInfoForm.old_house"
                  size="large"
                  type="textarea">
        </el-input>
      </el-form-item>
      <el-form-item label="备注" style="width: 65%">
        <el-input v-model="userInfoForm.notes" :autosize="{ minRows: 2, maxRows: 4 }" :value="userInfoForm.notes"
                  size="large"
                  type="textarea">
        </el-input>
      </el-form-item>
    </el-form>
  </div>
  <div style="text-align: center;justify-content: center; align-items: center">
    <el-button round size="large" type="default"
               @click="submit">保存
    </el-button>
  </div>

</template>

<style scoped>

</style>