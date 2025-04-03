<script setup>
import { ref, onMounted, computed } from 'vue'
import axios from 'axios'
import { ElMessage } from 'element-plus'
import { Search } from '@element-plus/icons-vue'

const API_BASE_URL = 'http://www.lhzzzzz.cn/table/api/users'

const users = ref([])
const loading = ref(false)
const searchQuery = ref('')

const fetchUsers = async () => {
  loading.value = true
  try {
    const response = await axios.get(`${API_BASE_URL}/api/users`)
    users.value = response.data
  } catch (error) {
    console.error('获取用户数据失败:', error)
    ElMessage.error('获取用户数据失败')
  } finally {
    loading.value = false
  }
}

const filteredUsers = computed(() => {
  if (!searchQuery.value) return users.value
  return users.value.filter(user => 
    user.name.toLowerCase().includes(searchQuery.value.toLowerCase())
  )
})

onMounted(() => {
  fetchUsers()
})
</script>

<template>
  <div class="container">
    <el-card class="box-card">
      <template #header>
        <div class="card-header">
          <h2>用户管理系统</h2>
          <el-button type="primary" @click="fetchUsers">刷新数据</el-button>
        </div>
      </template>
      
      <div class="search-box">
        <el-input
          v-model="searchQuery"
          placeholder="搜索用户名..."
          :prefix-icon="Search"
          clearable
        />
      </div>

      <el-table
        v-loading="loading"
        :data="filteredUsers"
        style="width: 100%"
        border
        stripe
        highlight-current-row
      >
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="name" label="用户名" />
        <el-table-column prop="password" label="密码">
          <template #default="scope">
            <el-tag type="info">{{ scope.row.password }}</el-tag>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<style scoped>
.container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
  display: flex;
  flex-direction: column;
  align-items: center;
}

h1 {
  text-align: center;
  margin-bottom: 30px;
  color: #409EFF;
}

.box-card {
  width: 100%;
  max-width: 800px;
  margin: 0 auto;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.card-header h2 {
  margin: 0;
  color: #409EFF;
  font-size: 24px;
}

.search-box {
  margin-bottom: 20px;
}

:deep(.el-table th) {
  background-color: #f5f7fa;
  color: #606266;
  font-weight: 600;
}

:deep(.el-table td) {
  padding: 12px 0;
}

:deep(.el-tag) {
  width: 100%;
  text-align: center;
}
</style>
