package com.seunome.leitorpdf // Lembre de mudar "seunome" para o seu

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.github.barteksc.pdfviewer.PDFView // Biblioteca de PDF

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppLeitorPDF()
                }
            }
        }
    }
}

@Composable
fun AppLeitorPDF() {
    // Estado que guarda o arquivo PDF escolhido pelo usuário
    var pdfUri by remember { mutableStateOf<Uri?>(null) }

    // Ferramenta que abre a galeria do celular para escolher o arquivo
    val seletorDePdf = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            pdfUri = uri // Salva o PDF escolhido
        }
    }

    // Lógica de navegação: Se tem PDF escolhido, mostra o leitor. Se não, mostra a tela inicial.
    if (pdfUri == null) {
        TelaInicial(
            onAbrirPdfClick = { seletorDePdf.launch("application/pdf") }
        )
    } else {
        TelaLeitorPDF(
            uri = pdfUri!!,
            onFecharClick = { pdfUri = null }
        )
    }
}

@Composable
fun TelaInicial(onAbrirPdfClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Ícone ou Emoji grande
        Text(
            text = "📄",
            fontSize = 72.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Título
        Text(
            text = "Leitor de PDF",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Subtítulo
        Text(
            text = "Seu leitor limpo, rápido e sem anúncios.",
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(48.dp))

        // Botão para abrir o PDF
        Button(
            onClick = onAbrirPdfClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(text = "Abrir um PDF", fontSize = 18.sp)
        }

        Spacer(modifier = Modifier.height(64.dp))

        // Informações do Desenvolvedor (Edite aqui!)
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Desenvolvido por: Seu Nome", fontWeight = FontWeight.Bold)
                Text(text = "Versão: 1.0", fontSize = 14.sp, color = Color.Gray)
                Text(text = "Atualizado em: Hoje", fontSize = 14.sp, color = Color.Gray)
            }
        }
    }
}

@Composable
fun TelaLeitorPDF(uri: Uri, onFecharClick: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        // Barra superior com botão de voltar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primaryContainer)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(onClick = onFecharClick) {
                Text(text = "⬅ Fechar PDF", color = MaterialTheme.colorScheme.onPrimaryContainer)
            }
        }

        // Onde o PDF é renderizado de verdade
        AndroidView(
            factory = { context ->
                PDFView(context, null).apply {
                    from(uri)
                        .enableSwipe(true)
                        .swipeHorizontal(false)
                        .enableDoubletap(true)
                        .defaultPage(0)
                        .load()
                }
            },
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        )
    }
}