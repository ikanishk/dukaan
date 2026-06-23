'use client'

import { useQuery } from '@tanstack/react-query'
import { apiClient } from '@/lib/api/client'
import { formatCurrency } from '@/lib/utils'
import { 
  Package, 
  ShoppingCart, 
  TrendingUp, 
  DollarSign,
  RefreshCw,
  Wrench
} from 'lucide-react'

interface DashboardData {
  totalDevices: number
  devicesInStock: number
  devicesSold: number
  devicesUnderRepair: number
  totalSalesToday: number
  totalSalesThisMonth: number
  revenueToday: number
  revenueThisMonth: number
  profitToday: number
  profitThisMonth: number
  expensesToday: number
  expensesThisMonth: number
  netProfitToday: number
  netProfitThisMonth: number
  returnsToday: number
  returnsThisMonth: number
}

export default function DashboardPage() {
  const { data, isLoading, error } = useQuery<DashboardData>({
    queryKey: ['dashboard'],
    queryFn: async () => {
      const response = await apiClient.get('/dashboard')
      return response.data
    },
  })

  if (isLoading) {
    return (
      <div className="flex min-h-screen items-center justify-center">
        <div className="text-center">
          <div className="h-12 w-12 animate-spin rounded-full border-4 border-blue-600 border-t-transparent mx-auto"></div>
          <p className="mt-4 text-gray-600">Loading dashboard...</p>
        </div>
      </div>
    )
  }

  if (error) {
    return (
      <div className="flex min-h-screen items-center justify-center">
        <div className="text-center">
          <p className="text-red-600 text-lg font-semibold">Error loading dashboard</p>
          <p className="mt-2 text-gray-600">{(error as Error).message}</p>
          <p className="mt-4 text-sm text-gray-500">Please check if the backend is running on port 8081</p>
        </div>
      </div>
    )
  }

  return (
    <div className="min-h-screen bg-gray-50 dark:bg-gray-900">
      <div className="mx-auto max-w-7xl px-4 py-8 sm:px-6 lg:px-8">
        <div className="mb-8">
          <h1 className="text-3xl font-bold text-gray-900 dark:text-white">
            Dashboard
          </h1>
          <p className="mt-2 text-sm text-gray-600 dark:text-gray-400">
            Overview of your business performance
          </p>
        </div>

        {/* Inventory Stats */}
        <div className="mb-8">
          <h2 className="mb-4 text-lg font-semibold text-gray-900 dark:text-white">
            Inventory
          </h2>
          <div className="grid gap-6 sm:grid-cols-2 lg:grid-cols-4">
            <StatCard
              title="Total Devices"
              value={data?.totalDevices || 0}
              icon={<Package className="h-6 w-6" />}
              color="blue"
            />
            <StatCard
              title="In Stock"
              value={data?.devicesInStock || 0}
              icon={<Package className="h-6 w-6" />}
              color="green"
            />
            <StatCard
              title="Sold"
              value={data?.devicesSold || 0}
              icon={<ShoppingCart className="h-6 w-6" />}
              color="purple"
            />
            <StatCard
              title="Under Repair"
              value={data?.devicesUnderRepair || 0}
              icon={<Wrench className="h-6 w-6" />}
              color="orange"
            />
          </div>
        </div>

        {/* Sales Stats */}
        <div className="mb-8">
          <h2 className="mb-4 text-lg font-semibold text-gray-900 dark:text-white">
            Sales Performance
          </h2>
          <div className="grid gap-6 sm:grid-cols-2 lg:grid-cols-3">
            <StatCard
              title="Sales Today"
              value={data?.totalSalesToday || 0}
              subtitle={formatCurrency(data?.revenueToday || 0)}
              icon={<ShoppingCart className="h-6 w-6" />}
              color="blue"
            />
            <StatCard
              title="Sales This Month"
              value={data?.totalSalesThisMonth || 0}
              subtitle={formatCurrency(data?.revenueThisMonth || 0)}
              icon={<ShoppingCart className="h-6 w-6" />}
              color="blue"
            />
            <StatCard
              title="Returns This Month"
              value={data?.returnsThisMonth || 0}
              icon={<RefreshCw className="h-6 w-6" />}
              color="red"
            />
          </div>
        </div>

        {/* Financial Stats */}
        <div>
          <h2 className="mb-4 text-lg font-semibold text-gray-900 dark:text-white">
            Financial Overview
          </h2>
          <div className="grid gap-6 sm:grid-cols-2 lg:grid-cols-4">
            <StatCard
              title="Profit Today"
              value={formatCurrency(data?.profitToday || 0)}
              icon={<TrendingUp className="h-6 w-6" />}
              color="green"
            />
            <StatCard
              title="Profit This Month"
              value={formatCurrency(data?.profitThisMonth || 0)}
              icon={<TrendingUp className="h-6 w-6" />}
              color="green"
            />
            <StatCard
              title="Expenses This Month"
              value={formatCurrency(data?.expensesThisMonth || 0)}
              icon={<DollarSign className="h-6 w-6" />}
              color="red"
            />
            <StatCard
              title="Net Profit This Month"
              value={formatCurrency(data?.netProfitThisMonth || 0)}
              icon={<DollarSign className="h-6 w-6" />}
              color="blue"
            />
          </div>
        </div>
      </div>
    </div>
  )
}

interface StatCardProps {
  title: string
  value: string | number
  subtitle?: string
  icon: React.ReactNode
  color: 'blue' | 'green' | 'purple' | 'orange' | 'red'
}

function StatCard({ title, value, subtitle, icon, color }: StatCardProps) {
  const colorClasses = {
    blue: 'bg-blue-500',
    green: 'bg-green-500',
    purple: 'bg-purple-500',
    orange: 'bg-orange-500',
    red: 'bg-red-500',
  }

  return (
    <div className="rounded-lg bg-white p-6 shadow dark:bg-gray-800">
      <div className="flex items-center justify-between">
        <div className="flex-1">
          <p className="text-sm font-medium text-gray-600 dark:text-gray-400">
            {title}
          </p>
          <p className="mt-2 text-3xl font-bold text-gray-900 dark:text-white">
            {value}
          </p>
          {subtitle && (
            <p className="mt-1 text-sm text-gray-500 dark:text-gray-400">
              {subtitle}
            </p>
          )}
        </div>
        <div className={`rounded-full p-3 ${colorClasses[color]} text-white`}>
          {icon}
        </div>
      </div>
    </div>
  )
}
