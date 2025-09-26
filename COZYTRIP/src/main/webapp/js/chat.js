class ChatManager {
    constructor(groupNum, userId, userNickname) {
        this.groupNum = groupNum;
        this.userId = userId;
        this.userNickname = userNickname;
        this.socket = null;
        this.messageHandlers = new Map();
        this.connected = false;
    }

    connect() {
        // WebSocket 연결 - 프로토콜 및 컨텍스트 경로 자동 감지
        const protocol = window.location.protocol === 'https:' ? 'wss:' : 'ws:';
        const contextPath = window.location.pathname.substring(0, window.location.pathname.indexOf('/', 1));
        
        // 컨텍스트 경로 포함하여 연결
        this.socket = new WebSocket(`${protocol}//${window.location.host}${contextPath}/websocket-chat`);
        
        console.log(`WebSocket 연결 시도: ${protocol}//${window.location.host}${contextPath}/websocket-chat`);
        console.log(`엔드포인트 통일 - /websocket-chat 사용`);
        
        this.socket.onopen = () => {
            this.connected = true;
            this.sendJoinMessage();
            console.log('WebSocket 연결 성공');
        };

        this.socket.onmessage = (event) => {
            const message = JSON.parse(event.data);
            const handler = this.messageHandlers.get(message.type);
            if (handler) {
                handler(message);
            }
        };

        this.socket.onclose = () => {
            this.connected = false;
            // 자동 재연결 시도
            setTimeout(() => this.connect(), 5000);
        };
    }

    registerHandler(messageType, handler) {
        this.messageHandlers.set(messageType, handler);
    }

    sendMessage(content) {
        if (!this.connected) return false;

        // 서버 측 코드와 일치하는 문자열 형식으로 메시지 전송
        // 형식: "chat:채팅방번호:사용자번호:사용자이름:메시지내용"
        const message = "chat:" + this.groupNum + ":" + this.userId + ":" + this.userNickname + ":" + content;
        
        this.socket.send(message);
        return true;
    }

    sendJoinMessage() {
        // 서버 측 코드와 일치하는 문자열 형식으로 조인 메시지 전송
        // 형식: "join:채팅방번호:사용자번호"
        const message = "join:" + this.groupNum + ":" + this.userId;
        
        this.socket.send(message);
    }

    disconnect() {
        if (this.socket) {
            this.socket.close();
        }
    }
}

class ChatUI {
    constructor(chatManager) {
        this.chatManager = chatManager;
        this.messageContainer = document.getElementById('chatMessages');
        this.memberContainer = document.getElementById('memberContainer');
        this.messageInput = document.getElementById('messageInput');
        this.sendButton = document.getElementById('sendButton');

        this.setupEventListeners();
        this.setupMessageHandlers();
    }

    setupEventListeners() {
        this.sendButton.addEventListener('click', () => this.sendMessage());
        
        this.messageInput.addEventListener('keypress', (e) => {
            if (e.key === 'Enter' && !e.shiftKey) {
                e.preventDefault();
                this.sendMessage();
            }
        });
    }

    setupMessageHandlers() {
        this.chatManager.registerHandler('CHAT', (message) => this.appendMessage(message));
        this.chatManager.registerHandler('JOIN', (message) => {
            this.appendSystemMessage(`${message.senderName}님이 입장하셨습니다.`);
            this.updateMemberList(message.members);
        });
        this.chatManager.registerHandler('LEAVE', (message) => {
            this.appendSystemMessage(`${message.senderName}님이 퇴장하셨습니다.`);
            this.updateMemberList(message.members);
        });
    }

    sendMessage() {
        const content = this.messageInput.value.trim();
        if (content && this.chatManager.sendMessage(content)) {
            this.messageInput.value = '';
        }
    }

    appendMessage(message) {
        const messageDiv = document.createElement('div');
        messageDiv.className = `message ${message.sender === this.chatManager.userId ? 'sent' : 'received'}`;
        
        const time = new Date(message.timestamp);
        const timeString = time.toLocaleTimeString('ko-KR', { hour: '2-digit', minute: '2-digit' });
        
        messageDiv.innerHTML = `
            <div class="message-content">${this.escapeHtml(message.content)}</div>
            <div class="message-info">
                <span class="message-sender">${this.escapeHtml(message.senderName)}</span>
                <span class="message-time">${timeString}</span>
            </div>
        `;
        
        this.appendToContainer(messageDiv);
    }

    appendSystemMessage(content) {
        const messageDiv = document.createElement('div');
        messageDiv.className = 'system-message';
        messageDiv.textContent = content;
        this.appendToContainer(messageDiv);
    }

    appendToContainer(element) {
        this.messageContainer.appendChild(element);
        this.messageContainer.scrollTop = this.messageContainer.scrollHeight;
    }

    updateMemberList(members) {
        this.memberContainer.innerHTML = '';
        members.forEach(member => {
            const memberDiv = document.createElement('div');
            memberDiv.className = 'member-item';
            memberDiv.innerHTML = `
                <span class="member-name">${this.escapeHtml(member.nickname)}</span>
                <span class="member-status ${member.online ? 'status-online' : 'status-offline'}"></span>
            `;
            this.memberContainer.appendChild(memberDiv);
        });
    }

    escapeHtml(unsafe) {
        return unsafe
            .replace(/&/g, "&amp;")
            .replace(/</g, "&lt;")
            .replace(/>/g, "&gt;")
            .replace(/"/g, "&quot;")
            .replace(/'/g, "&#039;");
    }
}
