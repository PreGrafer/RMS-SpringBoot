<script setup>
import {reactive, ref} from 'vue';
import {ElForm, ElFormItem, ElInput, ElButton, ElMessage} from 'element-plus';
import {useRoute} from "vue-router";
import {getDataByParam, postByJson} from "@/net";
import {Avatar, Edit, Link, Message, User, UserFilled, ZoomIn} from "@element-plus/icons-vue";
import router from "@/router";

const route = useRoute();
const manageUserInfoForm = reactive({
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
  note: ''
});

const genderOptions = [
  {value: '男', label: '男'},
  {value: '女', label: '女'},
  {value: '其它', label: '其它'}
]

const fetchUserData = () => {
  getDataByParam('/api/account/get-info', {username: route.params.manage}, (data) => {
    manageUserInfoForm.username = data.data.username;
    manageUserInfoForm.phone = data.data.phone;
    manageUserInfoForm.personid = data.data.personid;
    manageUserInfoForm.name = data.data.name;
    manageUserInfoForm.gender = data.data.gender;
    manageUserInfoForm.age = data.data.age;
    manageUserInfoForm.health_status = data.data.health_status;
    manageUserInfoForm.education_background = data.data.education_background;
    manageUserInfoForm.work_experience = data.data.work_experience;
    manageUserInfoForm.old_house = data.data.old_house;
    manageUserInfoForm.note = data.data.note;
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

const manageUserInfoFormRef = ref()

const back = () => {
  router.push(`/admin/accounts`)
};
const submit = () => {
  manageUserInfoFormRef.value.validate((isValid) => {
    if (isValid) {
      postByJson('/api/account/update-info', manageUserInfoForm, (message) => {
        ElMessage.success(message);
        back();
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
    <el-form ref="manageUserInfoFormRef" :model="manageUserInfoForm"
             :rules="rules" label-width="75px">
      <el-row>
        <el-form-item label="用户名" style="width: 18%">
          <el-input :value="manageUserInfoForm.username" readonly type="text">
            <template #prefix>
              <el-icon>
                <User/>
              </el-icon>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item label="身份证" style="width: 23%">
          <el-input :value="manageUserInfoForm.personid" readonly type="text">
            <template #prefix>
              <el-icon>
                <Link/>
              </el-icon>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item label="电话" prop="phone" style="width: 20%">
          <el-input v-model="manageUserInfoForm.phone" :value="manageUserInfoForm.phone" type="text">
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
          <el-input v-model="manageUserInfoForm.name" :value="manageUserInfoForm.name" type="text">
            <template #prefix>
              <el-icon>
                <Avatar/>
              </el-icon>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item label="性别" style="width: 15.5%">
          <el-select v-model="manageUserInfoForm.gender" :value="manageUserInfoForm.gender" placeholder="选择性别"
                     style="width: 100%">
            <el-option
                v-for="item in genderOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value"
            />
            <template #prefix>
              <el-icon>
                <UserFilled/>
              </el-icon>
            </template>
          </el-select>
        </el-form-item>
        <el-form-item label="年龄" prop="age" style="width: 15%">
          <el-input v-model="manageUserInfoForm.age" :value="manageUserInfoForm.age" type="number">
            <template #prefix>
              <el-icon>
                <ZoomIn/>
              </el-icon>
            </template>
          </el-input>
        </el-form-item>
      </el-row>
      <el-form-item label="健康状况" style="width: 65%">
        <el-input v-model="manageUserInfoForm.health_status" :autosize="{ minRows: 2, maxRows: 4 }"
                  :value="manageUserInfoForm.health_status"
                  size="large"
                  type="textarea">
        </el-input>
      </el-form-item>
      <el-form-item label="教育背景" style="width: 65%">
        <el-input v-model="manageUserInfoForm.education_background" :autosize="{ minRows: 2, maxRows: 4}"
                  :value="manageUserInfoForm.education_background"
                  size="large"
                  type="textarea">
        </el-input>
      </el-form-item>
      <el-form-item label="工作经历" style="width: 65%">
        <el-input v-model="manageUserInfoForm.work_experience"
                  :autosize="{ minRows: 2, maxRows: 4 }" :value="manageUserInfoForm.work_experience" size="large"
                  type="textarea">

        </el-input>
      </el-form-item>
      <el-form-item label="搬迁来源" style="width: 65%">
        <el-input v-model="manageUserInfoForm.old_house" :autosize="{ minRows: 2, maxRows: 4 }"
                  :value="manageUserInfoForm.old_house"
                  size="large"
                  type="textarea">
        </el-input>
      </el-form-item>
      <el-form-item label="备注" style="width: 65%">
        <el-input v-model="manageUserInfoForm.note" :autosize="{ minRows: 2, maxRows: 4 }"
                  :value="manageUserInfoForm.note"
                  size="large"
                  type="textarea">
        </el-input>
      </el-form-item>
    </el-form>
  </div>
  <div style="text-align: center;justify-content: center; align-items: center">
    <el-button round size="large" type="default" @click="submit">保存</el-button>
    <el-button round size="large" type="default" @click="back">返回</el-button>
  </div>

</template>

<style scoped>

</style>