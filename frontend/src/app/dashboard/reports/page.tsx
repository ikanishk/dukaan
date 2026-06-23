'use client'

import { FileText, Download, Calendar } from 'lucide-react'

export default function ReportsPage() {
  return (
    <div>
      <div className="mb-6">
        <h1 className="text-2xl font-bold text-gray-900 dark:text-white">
          Reports & Analytics
        </h1>
        <p className="mt-1 text-sm text-gray-600 dark:text-gray-400">
          Generate and download business reports
        </p>
      </div>

      <div className="grid gap-6 sm:grid-cols-2 lg:grid-cols-3">
        {/* Sales Report */}
        <div className="rounded-lg border border-gray-200 bg-white p-6 dark:border-gray-700 dark:bg-gray-800">
          <FileText className="h-8 w-8 text-blue-600" />
          <h3 className="mt-4 text-lg font-semibold text-gray-900 dark:text-white">
            Sales Report
          </h3>
          <p className="mt-2 text-sm text-gray-600 dark:text-gray-400">
            Detailed sales transactions and revenue analysis
          </p>
          <button className="mt-4 flex w-full items-center justify-center gap-2 rounded-lg bg-blue-600 px-4 py-2 text-sm font-semibold text-white hover:bg-blue-700">
            <Download className="h-4 w-4" />
            Generate Report
          </button>
        </div>

        {/* Inventory Report */}
        <div className="rounded-lg border border-gray-200 bg-white p-6 dark:border-gray-700 dark:bg-gray-800">
          <FileText className="h-8 w-8 text-green-600" />
          <h3 className="mt-4 text-lg font-semibold text-gray-900 dark:text-white">
            Inventory Report
          </h3>
          <p className="mt-2 text-sm text-gray-600 dark:text-gray-400">
            Current stock levels and inventory valuation
          </p>
          <button className="mt-4 flex w-full items-center justify-center gap-2 rounded-lg bg-green-600 px-4 py-2 text-sm font-semibold text-white hover:bg-green-700">
            <Download className="h-4 w-4" />
            Generate Report
          </button>
        </div>

        {/* Profit & Loss */}
        <div className="rounded-lg border border-gray-200 bg-white p-6 dark:border-gray-700 dark:bg-gray-800">
          <FileText className="h-8 w-8 text-purple-600" />
          <h3 className="mt-4 text-lg font-semibold text-gray-900 dark:text-white">
            Profit & Loss
          </h3>
          <p className="mt-2 text-sm text-gray-600 dark:text-gray-400">
            Comprehensive P&L statement with expenses
          </p>
          <button className="mt-4 flex w-full items-center justify-center gap-2 rounded-lg bg-purple-600 px-4 py-2 text-sm font-semibold text-white hover:bg-purple-700">
            <Download className="h-4 w-4" />
            Generate Report
          </button>
        </div>

        {/* Customer Report */}
        <div className="rounded-lg border border-gray-200 bg-white p-6 dark:border-gray-700 dark:bg-gray-800">
          <FileText className="h-8 w-8 text-orange-600" />
          <h3 className="mt-4 text-lg font-semibold text-gray-900 dark:text-white">
            Customer Report
          </h3>
          <p className="mt-2 text-sm text-gray-600 dark:text-gray-400">
            Customer transactions and outstanding balances
          </p>
          <button className="mt-4 flex w-full items-center justify-center gap-2 rounded-lg bg-orange-600 px-4 py-2 text-sm font-semibold text-white hover:bg-orange-700">
            <Download className="h-4 w-4" />
            Generate Report
          </button>
        </div>

        {/* Returns Report */}
        <div className="rounded-lg border border-gray-200 bg-white p-6 dark:border-gray-700 dark:bg-gray-800">
          <FileText className="h-8 w-8 text-red-600" />
          <h3 className="mt-4 text-lg font-semibold text-gray-900 dark:text-white">
            Returns Report
          </h3>
          <p className="mt-2 text-sm text-gray-600 dark:text-gray-400">
            Product returns and refund analysis
          </p>
          <button className="mt-4 flex w-full items-center justify-center gap-2 rounded-lg bg-red-600 px-4 py-2 text-sm font-semibold text-white hover:bg-red-700">
            <Download className="h-4 w-4" />
            Generate Report
          </button>
        </div>

        {/* Custom Report */}
        <div className="rounded-lg border border-gray-200 bg-white p-6 dark:border-gray-700 dark:bg-gray-800">
          <Calendar className="h-8 w-8 text-gray-600" />
          <h3 className="mt-4 text-lg font-semibold text-gray-900 dark:text-white">
            Custom Report
          </h3>
          <p className="mt-2 text-sm text-gray-600 dark:text-gray-400">
            Create custom reports with date range
          </p>
          <button className="mt-4 flex w-full items-center justify-center gap-2 rounded-lg bg-gray-600 px-4 py-2 text-sm font-semibold text-white hover:bg-gray-700">
            <Download className="h-4 w-4" />
            Generate Report
          </button>
        </div>
      </div>
    </div>
  )
}
