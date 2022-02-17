package com.alphagot.timealert

import io.netty.util.concurrent.SingleThreadEventExecutor
import kotlinx.coroutines.delay
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents.ClientStarted
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents.ServerStarted
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents
import net.minecraft.client.MinecraftClient
import net.minecraft.client.toast.SystemToast
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.text.TranslatableText
import net.minecraft.util.thread.TaskExecutor
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

// For support join https://discord.gg/v6v4pMv

class Main {
    private var ticks: Long = 0

    @Suppress("unused")
    fun init() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        println("Initialized!")
        val th = Executors.newSingleThreadScheduledExecutor()
        th.scheduleWithFixedDelay(
            {
                ticks++
                if (ticks % 20L == 1L && ticks > 5) {
                    val toast = SystemToast.create(
                        MinecraftClient.getInstance(),
                        SystemToast.Type.NARRATOR_TOGGLE,
                        if(ticks >= 480) { TranslatableText("toast.timealert.title_24") } else { TranslatableText("toast.timealert.title", (ticks) / 20L) },
                        TranslatableText("toast.timealert.message")
                    )
                    MinecraftClient.getInstance().toastManager.add(toast)
                } else {
                    println(ticks)
                }
            },
            0L,
            3L,
            TimeUnit.MINUTES
        )
    }
}
