package com.veco.vecoapp.presentation.misc

import com.veco.vecoapp.MR
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.desc.desc

open class Alert(val text: StringDesc, val emoji: String)

sealed class AlertType {
    object TaskCompletion : Alert(MR.strings.alert_task_completed.desc(), "\uD83C\uDF89")
    object ConnectionIssue : Alert(MR.strings.alert_connection_issue.desc(), "⚙️")
    object MailSent : Alert(MR.strings.alert_mail_sent.desc(), "✉️️")
    object TaskSent : Alert(MR.strings.alert_task_sent.desc(), "\uD83D\uDCDD️")
    class CommonAlert(msg: StringDesc) : Alert(msg, "⚙️")
}
