package com.example.taskhabit

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

// @HiltAndroidApp transforma essa classe no ponto de entrada do Hilt.
// Ele gera o "container" raiz que vai guardar e prover
// todas as dependências do app (database, repositories, etc.)
@HiltAndroidApp
class HabitApplication : Application()
