# Kubernetes

Kubernetes é um plataforma para o gerenciamento de contêineres docker (Não necessariamente docker), que facilita tanto a configuração declarativa quanto a automação dos mesmos.

> **O Docker é a ferramenta padrão para deployar/implementar uma aplicação usando containers. A grande vantagem de um container é encapsular todas as dependências necessárias para rodá-lo**, como bibliotecas, o runtime e o código da aplicação. Tudo isso em um único pacote chamado de imagem, que pode ser versionado e fácil de distribuir.
> 

As funcionalidades do kubernetes são disponibilizados para o usuário por meio de API’s. Para facilitar a interação do usuário com essas funcionalidades, podemos usar o Kubectl que é um CLI que abstrai essas chamadas.

### Arquitetura do K8s

O K8s pode ser instalado em modo single node ou pode ser instalado como um cluster (Com mais de um node ou maquina).

Cluster - Conjunto de máquinas 

Node (nó) - Maquina que roda o kubernetes. Pode ser física ou virtual. Cada uma terá sua própria quantidade de vCPU e Memória. Um nó pode ter “n” Pods rodando nele.

- Quando estamos trabalhando num cluster, temos a figura de nodes(nós) Master e Worker.
    - Master - Executa os componentes do plano de controle. Quando mandamos algum comando via Kubectl, é esse cara quem recebe esses comandos e delega a algum worker executar de fato. É possível ter mais de um nó master mas isso seria vantajoso quando tenho muitos nós workers. No exemplo abaixo não faria muito sentido, visto que são 3 workers apenas.
    - Worker - Quem geralmente executa os containers de fato.
    
    ![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/1bc51670-3125-484d-bb44-8b63c0008d28/Untitled.png)
    

### POD

É a unidade que contém os containers provisionados, são os processos rodando no cluster.

É um invólucro que envolve o container. Ele disponibiliza entre outros, um IP e um DNS para que o container possa ser acessado.

- É uma unidade do Kubernetes que consegue gerenciar um ou mais containers dentro dele.

O exemplo abaixo é o que se chama de manifesto.

### Criando POD

```yaml
# Qual a versão do Kubertes
apiVersion: v1
# O que estou criando? Nesse caso um POD
kind: Pod
# Metadados que eu posso usar pra fazer buscas internamente depois
metadata:
# Um dos metadatas possiveis é o nome do POD. A esse estou dando nome de static-web
  name: static-web
# Criando um variavel para filtros futuros(Nesse caso "role" com o valor myrole. Chave valor.
  labels:
    role: myrole
#Especificações sobre o POD
spec:
#A Boa prática é usar um container por POD. Mas é possível usar mais que um
#Como eu posso rodar mais de um container dentro de um POD, aqui eu defino quem são
  containers:
    - name: web
      image: nginx
      ports:
        - name: web
          containerPort: 80
          protocol: TCP
```

Os pods em um cluster do Kubernetes são usados de duas maneiras principais:

- **Pods que executam um único contêiner**. O modelo "um contêiner por pod" é o caso de uso mais comum do Kubernetes; neste caso, o Pod funciona como um invólucro em torno de um único recipiente; O Kubernetes gerencia Pods em vez de gerenciar os contentores directamente.
- **Pods que executam vários contêineres que precisam trabalhar juntos**. Uma Pod consegue encapsular um aplicativo composto de vários contêineres co-localizados que são fortemente acoplados e têm necessidade de compartilhar recursos. Esses contêineres co-localizados formam uma única unidade de serviço coesa — por exemplo, um contêiner que serve dados armazenado em um volume compartilhado para o público, enquanto um contêiner *sidecar* separado atualiza ou atualiza esses arquivos. O Pod encapsula esses contêineres, recursos de armazenamento e uma rede efêmera identidade juntos como uma única unidade.

> **Nota: O agrupamento de vários contêineres co-localizados e co-gerenciados em um único Pod é um caso de uso relativamente avançado. Você deve usar esse padrão somente em instâncias em que seus contêineres estão bem acoplados.**
> 

### Replicaset

É o cara responsável pelo autoscaling. Se eu digo que preciso ter n POD’s em pé, o Replicaset fica vistoriando isso e se cair um POD, ele sobe outro pra manter o numero de POD’s que eu estabeleci de pé. Podemos ter um arquivo definindo o replicaset como o exemplo abaixo mas conseguimos fazer isso também diretamente na declaração do deployment.

### Criando Replicaset

```yaml
# Namespace onde vai rodar o replicaset
apiVersion: apps/v1
# Estou criando um replicasr
kind: ReplicaSet
metadata:
# Dei um nome a ele, assim como fiz com o pod
  name: my-replicaset
spec:
# Estou dizendo quantas replicas eu quero
  replicas: 2
# Essa regra vai ser aplicada a todos que tiverem o nome do app for "my-app"
  selector:
    matchLabels:
      app: my-app
# Qual é o POD que vai ser criado dentro desse replicaset
  template:
    metadata:
      labels:
        app: my-app
    spec:
      containers:
      - name: my-container
        image: nginx
```

### Deployement

Um deployement fornece atualizações declarativas para PODs e Replicasets. Ou seja:

- Se eu fizer alguma mudança nos PODs q rodam dentro do Replicaset, ele vai fazer com que o replicaset mate todos os pods e suba com as novas configurações

Para construir um deployment é só fazer o mesmo manifesto do replicaset, só que colocando o `kind: Deployment`. Por debaixo dos panos o Kubernetes vai criar um replicaset pra ficar debaixo desse deployment.

O arquivo do Deployment é o mesmo do replicaset mas com o kind diferente.

### Construindo Deployment

```yaml
# Namespace onde vai rodar o replicaset
apiVersion: apps/v1
# Estou criando um deployment
kind: Deployment
metadata:
# Dei um nome a ele, assim como fiz com o pod
  name: my-deployment
spec:
# Estou dizendo quantas replicas eu quero (Fazendo o trabalho do replicaset)
  replicas: 2
# Essa regra vai ser aplicada a todos que tiverem o nome do app for "my-app"
  selector:
    matchLabels:
      app: my-app
# Qual é o POD que vai ser criado dentro desse replicaset
  template:
    metadata:
      labels:
        app: my-app
    spec:
      containers:
      - name: my-container
        image: nginx
```

### Namespace

No Kubernetes, *os namespaces* fornecem um mecanismo para isolar grupos de recursos em um único cluster. Os nomes dos recursos precisam ser exclusivos dentro de um namespace, mas não entre namespaces.

Os namespaces são uma maneira de dividir os recursos de cluster entre vários usuários.

### Labels

*Labels* são pares chave/valor anexados a objetos, como pods. Destinam-se a ser usados para especificar atributos de identificação de objetos que são significativos e relevantes para os usuários, mas não implicam diretamente semântica para o sistema principal. Podem ser usados para organizar e selecionar subconjuntos de objetos.

### Service

O Service é a porta de entrada para acessar as aplicações rodando no POD. O Kubernetes fornece aos Pods seus próprios endereços IP e um único nome DNS para um conjunto de Pods, e pode balancear a carga entre eles. O conjunto de Pods visados por um Serviço é geralmente determinado por um [seletor](https://kubernetes.io/docs/concepts/overview/working-with-objects/labels/).

> Através de um *selector de labels*, o cliente/usuário pode identificar um conjunto de objetos. O selector é a primitiva de agrupamento principal no Kubernetes.
> 

Com isso ele também atua como loadbalancer. Se tiver 10 pods rodando, ele vai escolher um e vai permitir o acesso a aquele pod.

### Criando Service

```yaml
apiVersion: v1
kind: Service
metadata:
  name: goserver-service
spec:
#Responsavel por filtrar todos os pods que estarão associados a esse service
  selector:
#Pegue todos os Pods que tenham o "app" declarado no matchlabels como "goserver"  
    app: goserver
  type: ClusterIP
#  Para expor meu serviço a internet, eu preciso usaro Loadbalancer
#  type: LoadBalancer
#Forma mais arcaica (muito raro de se usar)
#  type: NodePort
  ports:
  - name: goserver-service
    port: 80
    #TargetPort é a porta doo container. Sempre que eu quiser direcionar a requisição vinda do service
    #para alguma porta específica do container eu uso o targetPort
    #Nesse exemplo o svc vai receber a requisição na porta 80 e vai mandar pra porta 8081 do container
    #Se eu não especificar o targetPort ele vai assumir que a porta do container é a mesma que o service
#   targetPort: 8081
#   nodePort: valor entre 30000 a 32767
    protocol: TCP
```

**Port** - Porta do Service

**Target Port** - Por do pod

Com o comando abaixo eu consigo direcionar um ponto da rede para outro. Ou seja, toda requisição recebida na porta 9000 será direcionada para a porta 80.

```bash
kubectl port-forward svc/nomedoservice-service 9000:80
```

### Variaveis de ambiente

Na declaração do container eu posso criar um campo env na declaração do container iae eu consigo pegar essas variaveis dentro da aplicação rodando no container.

- Ponto negativo dessa abordagem é o fato de deixar isso hard coded no arquivo.

```yaml
    spec:
      containers:
        - name: goserver
          image: "montivaljunior/hello-go:latest"    
          env:
            - name: nome
              value: "montival junior"
            - name: idade
              value: "32"
```

Outra possibilidade é com o Config map

Primeiro crio um arquivo de config map. No exemplo eu criei um goserver-env.yaml

```yaml
apiVersion: v1
kind: ConfigMap
metadata:
  name: goserver-env
data:
  nome: "montival junior"
  idade: "32"
```

Agora eu posso pegar ele no arquivo de declaração do pod, assim:

```yaml
spec:
      containers:
        - name: goserver
          image: "montivaljunior/hello-go:latest"    
          env:
            - name: nome
              valueFrom: 
                configMapKeyRef:
                  name: goserver-env
                  key: nome
            - name: idade
              valueFrom: 
                configMapKeyRef:
                  name: goserver-env
                  key: idade
```

Seu mudar o config map, eu vou precisar aplicar novamente o deployment pra ele poder fazer a atualização. **Não é automático.**

Há ainda uma terceira forma, que é a mais fácil.

```yaml
spec:
      containers:
        - name: goserver
          image: "montivaljunior/hello-go:latest"    
          envFrom:
            - configMapRef:
                name: goserver-env
```

Dessa forma ele irá carregar todos os dados cadastrados no config map .

### Liveness Probe

Indica se o contêiner está em execução. Se a liveness probe falha, o kubelet mata o pod e o pod fica sujeito à [política de reinicialização](https://kubernetes.io/docs/concepts/workloads/pods/pod-lifecycle/#restart-policy). Se um contêiner não fornecer uma liveness probe, o estado padrão é `Success`

É um diagnóstico realizado periodicamente pelo [kubelet](https://kubernetes.io/docs/reference/command-line-tools-reference/kubelet/) em um POD. Para executar um diagnóstico, o kubelet executa o código dentro do contêiner ou faz uma solicitação de rede.

### Parametrizando Liveness Probe

```yaml
spec:
      containers:
        - name: goserver
          image: "montivaljunior/hello-go:latest"
# Como checarei a saúde do meu container?          
          livenessProbe:
#Usarei uma comunicação via http          
            httpGet:
#Consultarei no path /health            
              path: /health
#Na porta 8080 do CONTAINER, não é a porta do service 
              port: 8080
# De 5 em 5 segundos
            periodSeconds: 5
# Quantas vezes a resposta tem q ser negativa pra que eu assuma
#Que a aplicação caiu?
            failureThresold: 1
# Quantos segundos eu vou esperar uma resposta?
            timeoutSeconds: 1
# Quantas vezes tenho que bater no path pra considerar que está tudo ok
            successTrhreshold: 3
```

### Readness

Responsável por verificar quando a aplicação está pronta pra começar a receber as requisições.

As vezes quando um container sobe, ele ainda não está pronto. Como acontece com algumas aplicações Spring. O container é criado mas para a aplicação subir mesmo precisa subir kafka, conectar etc etc..

Nesse período de configuração da aplicação inicial, eu não quero receber requisições, ficando assim a cargo do readness me dizer quando que a aplicação estará ok.

### Parametrizando Readness

```yaml
spec:
      containers:
        - name: goserver
          image: "montivaljunior/hello-go:latest"
# Como checarei a saúde do meu container?          
          readnessProbe:
#Usarei uma comunicação via http          
            httpGet:
#Consultarei no path /health            
              path: /health
#Na porta 8080 do CONTAINER, não é a porta do service 
              port: 8080
# De 5 em 5 segundos
            periodSeconds: 5
# Quantas vezes a resposta tem q ser negativa pra que eu assuma
#Que a aplicação não está pronta?
            failureThresold: 1
# Aguarda 10 segundos pra começar a pingar o /health
						initialDelaySeconds: 10
```

### Combinando Liveness + Readness

Para usar os dois juntos, o readness sempre tem que dar um sinal de ok primeiro senão o liveness irá sempre reiniciar o container. 

- Uma alternativa é colocar um delay maior no liveness, assim dará tempo do readness dizer que está tudo bem.

> O **Readness** não verifica apenas na inicialização do container. Ele fica o tempo todo vendo se a aplicação está ready. Ele não quer saber se o container está em pé, ele quer saber se a aplicação está pronta. **Ele tira o tráfego caso não esteja ready.**
O  **Liveness** verifica se o container está pronto. **Ele recria o container caso o container morra.**
> 

### Parametrizando Liveness + Readness juntos

```yaml
spec:
      containers:
        - name: goserver
          image: "montivaljunior/hello-go:latest"
          readnessProbe:
#Usarei uma comunicação via http          
            httpGet:
#Consultarei no path /health            
              path: /health
#Na porta 8080 do CONTAINER, não é a porta do service 
              port: 8080
# De 5 em 5 segundos
            periodSeconds: 5          
# Quantas vezes a resposta tem q ser negativa pra que eu assuma
#Que a aplicação está ready?
            failureThresold: 1
# Aguarda 10 segundos pra começar a pingar o /health
            initialDelaySeconds: 10    

          livenessProbe:      
            httpGet:    
              path: /health
              port: 8080
            periodSeconds: 5
            failureThresold: 1
# Quantos segundos eu vou esperar uma resposta?
            timeoutSeconds: 1
# Quantas vezes tenho que bater no path pra considerar que está tudo ok
            successTrhreshold: 1
            initialDelaySeconds: 15
```

Isso funciona quando eu sei quanto tempo a aplicação demora pra startar. Caso eu não saiba, eu não tenho como colocar um initial delay perfeito. NEsse caso eu preciso usar o startupProbe.

O StartupProbe atua somente na inicialização da aplicação e ele só libera parao liveness e o readness trabalharem, quando a aplicação já estiver ready.

### Parametrizando StartupProbe

```yaml
spec:
      containers:
        - name: goserver
          image: "montivaljunior/hello-go:latest"
          startupProbe:
#Usarei uma comunicação via http          
            httpGet:
#Consultarei no path /health            
              path: /health
#Na porta 8080 do CONTAINER, não é a porta do service 
              port: 8080
# De 3 em 3 segundos
            periodSeconds: 3          
# Quantas vezes a resposta tem q ser negativa pra que eu assuma
# que a aplicação está pronta? de 3 em 3 segundos por 30 vezes vai
# dar 1 minuto e meio...tempo suficiente pra uma app subir.
            failureThresold: 30

          readnessProbe:
#Usarei uma comunicação via http          
            httpGet:
#Consultarei no path /health            
              path: /health
#Na porta 8080 do CONTAINER, não é a porta do service 
              port: 8080
            periodSeconds: 3          
# Quantas vezes a resposta tem q ser negativa pra que eu assuma
#Que a aplicação está ready?
            failureThresold: 1  

          livenessProbe:      
            httpGet:    
              path: /health
              port: 8080
            periodSeconds: 5
            failureThresold: 1
# Quantos segundos eu vou esperar uma resposta?
            timeoutSeconds: 1
# Quantas vezes tenho que bater no path pra considerar que está tudo ok
            successTrhreshold: 1
```

Como o startupProbe ja faz o initial delay, eu não preciso colocar mais delay no readness ou liveness.

### Ingress

Relembrando um pouco sobre services… O Service é a porta de entrada para acessar as aplicações rodando no POD. Quando falamos especificamente sobre o service do type loadbalancer, esse type gera um IP externo que permite que o Pod seja acessado de fora do Kubernetes.

O Ingress tem essa mesma ideia mais com alguns pontos de melhoria. 

- É um service do tipo LoadBalancer que possui um IP.

Digamos que eu tenho 10 serviços. Cada vez que eu quiser acessar algum deles, eu vou bater no IP do ingress.

- Daí baseado no hostname, no path e etc ele será redirecionado para o serviço que queremos.
- Lembra muito um api gateway e um proxy reverso.

> Um proxy reverso **é um servidor que fica na frente dos servidores web e encaminha as solicitações do cliente (por exemplo, navegador web) para esses servidores web**
. Os proxy reversos normalmente são implementados para ajudar a aumentar a segurança, o desempenho e a confiabilidade.
> 

> **API Gateway** é uma espécie de filtro de entrada, que direciona os dados e chamadas ao local mais adequado. Gerencia o tráfego que faz interface com o serviço de back-end real e aplica políticas, autenticação e controle de acesso geral para chamadas de APIs de forma a proteger os dados do servidor.
> 

### Criando Ingress

```yaml
apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
  name: ingress-host
# Cada sistema que vai usar esse ingress pode pegar essa annotation para interpretar pra usar em alguma funcionalidade
  annotations:
 # Esse ingress usará um ingress do nginx (è como se fosse um adaptador) 
    kubernetes.io/ingress.class: "nginx"
spec:
# Regras
  rules:
# Qual host vai utilizar esse ingress?  
  - host: "ingress.monthalcantara.com.br"
    http:
      paths:
# Essa regra vale pra cada chamada que o prefixo for / por exemplo ingress.monthalcantara.com.br/      
      - pathType: Prefix
        path: "/"
#Qual nome do serviço que eu vou querer acessar com essa regra?        
        backend:
          service:
            name: goserver-service
            port: 
              number: 80
```
