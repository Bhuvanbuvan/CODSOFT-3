package com.example.alarmapp

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.TimePickerDialog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.alarmapp.domin.AlarmItem2
import com.example.alarmapp.domin.Rooddb
import com.example.alarmapp.domin.repository.Reposito
import com.example.alarmapp.domin.viewmodel.ViewModel
import com.example.alarmapp.ui.theme.AlarmAppTheme
import com.google.android.material.timepicker.TimeFormat
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.Calendar
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val context=this.applicationContext
        setContent {
            AlarmAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    HomeScreen(context = context)
                }
            }
        }
    }
}

@SuppressLint("RememberReturnType")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(context:Context){

    val dbapi=Rooddb.getinsetence(context)
    val reposi=Reposito(dbapi.getado())
    val viewmodel=ViewModel(reposi)

    val items by viewmodel.getalrm().collectAsState(initial = emptyList())
    val scheduler=AndroidAlamScheduler(context)
    var alarmItem:AlarmItem?=null


    var hour by remember { mutableStateOf("0")  }
    var minite by remember { mutableStateOf("0")  }
    var second by remember { mutableStateOf("0")  }
    var AmOrPm by remember { mutableStateOf("0")  }


    var message by remember {
        mutableStateOf("")
    }
    var thour by remember { mutableStateOf("")  }
    var tminite by remember { mutableStateOf("")  }
    var tAmOrPm by remember { mutableStateOf("")  }

    LaunchedEffect(Unit) {
        while (true){
            val cal =Calendar.getInstance()
            hour=cal.get(Calendar.HOUR).run {
                if (this.toString().length==1) "0$this" else "$this"
            }

            minite=cal.get(Calendar.MINUTE).run {
                if (this.toString().length==1) "0$this" else "$this"
            }

            second=cal.get(Calendar.SECOND).run {
                if (this.toString().length==1) "0$this" else "$this"
            }

            AmOrPm=cal.get(Calendar.AM_PM).run {
                if (this==Calendar.AM) "Am" else "Pm"
            }

            delay(1000)
        }

    }

    Scaffold(
        bottomBar = {
            NavigationBar(modifier = Modifier.shadow(elevation = 4.dp),
                containerColor =Color.Yellow) {

                NavigationBarItem(selected = true,
                    onClick = {

                    },
                    label = {
                            Text(text = "Home")
                    },
                    icon = {
                        Icon(painter = painterResource(id = R.drawable.baseline_add_home_24), contentDescription = "")
                    })
            }
        }
    ) {paddingValues ->
        Column(
            Modifier
                .padding(paddingValues = paddingValues)
                .fillMaxSize()
                .padding(top = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Row {
                Text(text = "$hour : $minite :$second   $AmOrPm",
                  fontSize = 32.sp ,
                    fontWeight = FontWeight(800)
                )
            }
            Spacer(modifier = Modifier.height(30.dp))
            Text(text = "Set Alarm",
                fontSize = 32.sp,
                fontWeight = FontWeight(800),
                color = Color.Red
                )
            Spacer(modifier = Modifier.height(30.dp))
            Row {
                OutlinedTextField(value =thour , onValueChange ={
                    thour=it
                }, modifier = Modifier
                    .width(90.dp)
                    .height(70.dp),
                    label = { Text(text = "Hour")})
                
                Text(text = ":", fontSize = 52.sp)

                OutlinedTextField(value =tminite , onValueChange ={
                    tminite=it
                }, modifier = Modifier
                    .width(90.dp)
                    .height(70.dp),
                    label = { Text(text = "Minite")})

                Text(text = ":", fontSize = 52.sp)


                OutlinedTextField(value =tAmOrPm , onValueChange ={
                    tAmOrPm=it
                }, modifier = Modifier
                    .width(90.dp)
                    .height(70.dp),
                    label = { Text(text = "Am/Pm")})
            }
            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(value =message , onValueChange = {msg->
                message=msg
            })

            Spacer(modifier = Modifier.height(40.dp))
            FloatingActionButton(onClick = {
                if (thour>=hour && tminite>minite && tAmOrPm==AmOrPm){
                    alarmItem= AlarmItem(time = LocalDateTime.now()
                        .plusMinutes((tminite.toInt()-minite.toInt()).toLong()),
                        message.toString())
                    alarmItem?.let (scheduler::sheduler)
                    viewmodel.insalam(AlarmItem2(0, alarmItem!!.time.toString(),message))
                    thour=""
                    tminite=""
                    tAmOrPm=""
                    message=""
                    Log.i("TAG","${alarmItem!!.time}")
                }
                else if ((thour>=hour||thour<hour) && (tminite<minite||tminite>=minite) && tAmOrPm!=AmOrPm){
                    alarmItem= AlarmItem(time = LocalDateTime.now()
                        .plusHours((12-hour.toInt()).toLong() + thour.toLong())
                        ,message.toString())
                    alarmItem?.let (scheduler::sheduler)
                    thour=""
                    tminite=""
                    tAmOrPm=""
                    Log.i("TAG","${alarmItem!!.time}")
                }
                                          /* alarmItem= AlarmItem(time = LocalDateTime.now()
                                               .plusSeconds(setalarm.toLong()))
                                            alarmItem?.let (scheduler::sheduler)
                                            setalarm=""
                                            Log.i("TAG","${alarmItem!!.time}")
                */
                                           },
                containerColor = colorResource(id = R.color.purple_200)) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "")
            }



            //List
            LazyColumn {
                items(items){alarmit->
                    alarmite(alarmit)
                }
            }


        }

    }
}


@Composable
fun alarmite(alarmItem2: AlarmItem2){

    var ischecked by remember {
        mutableStateOf(false)
    }
    Card(modifier = Modifier
        .fillMaxWidth()
        .height(150.dp)
        .padding(12.dp)) {
        Row(modifier = Modifier.padding(10.dp)
        , verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.padding(10.dp)) {
                Text(text = alarmItem2.time,
                    fontSize = 23.sp,
                    color = Color.Black,
                    )
                Text(text = alarmItem2.message,
                    fontSize = 23.sp,
                    color = Color.Black)
            }
            RadioButton(selected = ischecked, onClick = {
                if (ischecked){
                    ischecked=false
                }else
                    ischecked=true
            }
                )
        }
    }
}