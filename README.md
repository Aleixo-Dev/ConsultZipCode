<h1 align="center">🗺 Buscar Cep 🗺</h1>

<p align="center">
  <a href="https://opensource.org/licenses/Apache-2.0"><img alt="License" src="https://img.shields.io/badge/License-Apache%202.0-blue.svg"/></a>
  <a href="https://android-arsenal.com/api?level=23"><img alt="API" src="https://img.shields.io/badge/API-23%2B-brightgreen.svg?style=flat"/></a>
</p>

<p align="center">  
🔍 projeto feito com o objetivo de consultar cep e ddd de diversas cidade de todo brasil, é possível também visualizar a localização onde se encontra determinado cep.
</p>

<img  width="90%" src="https://user-images.githubusercontent.com/75820713/208762224-4fa5137f-d0aa-41aa-93c0-71bc3e2310a4.png"/>

## Screenshot 📸
| Buscar DDD | Buscar Cep | Mapa Preview |
| :--------------------: | :--------------------: | :--------------------: |
| ![01](https://github.com/Aleixo-Dev/ConsultZipCode/assets/75820713/175ee0c6-adc5-4291-8458-d068ef320043) | ![02](https://github.com/Aleixo-Dev/ConsultZipCode/assets/75820713/10ba0cb8-eda2-4727-8055-793eab49c767) | ![03](https://github.com/Aleixo-Dev/ConsultZipCode/assets/75820713/d98e7c42-4889-4857-b8a9-b342249dcde6) |

<a href="https://play.google.com/store/apps/details?id=br.com.nicolas.consultacd">
<img  width="20%" src="https://play.google.com/intl/en_us/badges/static/images/badges/pt_badge_web_generic.png"/>

## 📌 Tecnologias utilizadas.

- [Linguagem Kotlin](https://kotlinlang.org/)

- Jetpack
  - RecyclerView: Criar lista de forma dinâmicas
  - Lifecycle: Observe os ciclos de vida do Android e manipule os estados da interface do usuário após as alterações do ciclo de vida.
  - ViewModel: Gerencia o detentor de dados relacionados à interface do usuário e o ciclo de vida. Permite que os dados sobrevivam a alterações de configuração, como rotações de tela.
  - ViewBinding: Liga os componentes do XML no Kotlin através de uma classe que garante segurança de tipo e outras vantagens.
  - Flow: Fluxo que emit multiplos valores sequencialmente.

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
