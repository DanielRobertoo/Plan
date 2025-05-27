package com.example.plan.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.plan.ui.graph.EntryAppGraph
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import com.example.plan.ui.graph.appGraph
import com.example.plan.ui.graph.loginGraph
import java.lang.reflect.Modifier

