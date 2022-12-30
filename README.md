<h1 align="center">🗺 Consultar CD 🗺</h1>

<p align="center">
  <a href="https://opensource.org/licenses/Apache-2.0"><img alt="License" src="https://img.shields.io/badge/License-Apache%202.0-blue.svg"/></a>
  <a href="https://android-arsenal.com/api?level=23"><img alt="API" src="https://img.shields.io/badge/API-23%2B-brightgreen.svg?style=flat"/></a>
  <br>
  <a href="https://www.linkedin.com/in/nicolas-aleixo/"><img alt="Linkedin" src="https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white"/></a>
  <a href="mailto:nicolasaleixo2020@gmail.com"><img alt="Gmail" src="https://img.shields.io/badge/Gmail-D14836?style=for-the-badge&logo=gmail&logoColor=white"/></a>
</p>

<p align="center">  
🔍 projeto simples feito com o objetivo de consultar cep e ddd de diversas cidade de todo brasil, é possível também visualizar a localização onde se encontra aquele cep 
</p>

<a href="https://play.google.com/store/apps/details?id=br.com.nicolas.consultacd">
<img  width="20%" src="https://play.google.com/intl/en_us/badges/static/images/badges/pt_badge_web_generic.png"/>
<img  width="90%" src="https://user-images.githubusercontent.com/75820713/208762224-4fa5137f-d0aa-41aa-93c0-71bc3e2310a4.png"/>

## 📌 Tecnologias utilizadas

- [Linguagem Kotlin](https://kotlinlang.org/)

- Jetpack
  - Lifecycle: Observe os ciclos de vida do Android e manipule os estados da interface do usuário após as alterações do ciclo de vida.
  - ViewModel: Gerencia o detentor de dados relacionados à interface do usuário e o ciclo de vida. Permite que os dados sobrevivam a alterações de configuração, como rotações de tela.
  - ViewBinding: Liga os componentes do XML no Kotlin através de uma classe que garante segurança de tipo e outras vantagens.
  - Flow: Fluxo que emit multiplos valores sequencialmente

- Arquitetura
  - MVVM (View - ViewModel - Model)
  - Comunicação da ViewModel com a View através de LiveData
  - Comunicação da ViewModel com a Model através de Kotlin Flow
  - Repositories para abstração da comunidação com a camada de dados.
  
- Firebase
  - Analytics - Obter a quantidade e tempo de usúarios usando o aplicativo.
  - Crashlytics - Garantir que quando houver crash, indentificar de maneira rápida.

  
- Bibliotecas
  - [Retrofit2](https://github.com/square/retrofit): Para realizar requisições seguindo o padrão HTTP.
  - [Koin](https://insert-koin.io/): Para injeção de dependência
  - [SplashScreen](https://developer.android.com/develop/ui/views/launch/splash-screen): Criar splash screen de maneira facíl
  - [Maps](https://developers.google.com/maps): Visualizar no mapa a localização do cep

# Licença
```xml

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

Google Play e o logótipo do Google Play são marcas comerciais da Google LLC.
