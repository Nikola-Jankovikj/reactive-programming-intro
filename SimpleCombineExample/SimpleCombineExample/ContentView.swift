//
//  ContentView.swift
//  SimpleCombineExample
//
//  Created by Nikola Jankovikj on 7.12.24.
//

import SwiftUI
import Combine

class ViewModel: ObservableObject {
    
    @Published var textFieldText: String = ""
    @Published var textIsValid: Bool = false
    @Published var showButton: Bool = false
    var cancellables = Set<AnyCancellable>()
    
    init() {
        addTextFieldSubscriber()
        addButtonSubscriber()
    }
    
    func addTextFieldSubscriber() {
        $textFieldText
            .debounce(for: .seconds(0.5), scheduler: DispatchQueue.main)
            .removeDuplicates()
            .map { text -> Bool in
                return text.count > 3
            }
            .sink(receiveValue: { [weak self] isValid in
                self?.textIsValid = isValid
            })
            .store(in: &cancellables)
    }
    
    func addButtonSubscriber() {
        $textIsValid
            .sink { [weak self] isValid in
                guard let self = self else { return }
                if isValid {
                    self.showButton = true
                }
                else {
                    self.showButton = false
                }
            }
            .store(in: &cancellables)
    }
    
}

struct ContentView: View {
    
    @StateObject var viewModel = ViewModel()
    
    var body: some View {
        VStack {
            TextField("Type something here...", text: $viewModel.textFieldText)
                .padding(.leading)
                .frame(height: 55)
                .font(.headline)
                .background(Color(#colorLiteral(red: 0.921431005, green: 0.9214526415, blue: 0.9214410186, alpha: 1)))
                .cornerRadius(10)
                .overlay (
                    ZStack {
                        Image(systemName: "xmark")
                            .foregroundStyle(.red)
                            .opacity(viewModel.textFieldText.count < 1 ? 0.0 :
                                        viewModel.textIsValid ? 0.0 : 1.0)
                        
                        Image(systemName: "checkmark")
                            .foregroundStyle(.green)
                            .opacity(viewModel.textIsValid ? 1.0 : 0.0)
                    }
                    .font(.title)
                    .padding(.trailing)
                 
                    , alignment: .trailing
                )
            
            Button(action: {}, label: {
                Text("Submit".uppercased())
                    .foregroundStyle(.white)
                    .frame(height: 55)
                    .frame(maxWidth: .infinity)
                    .background(Color.blue)
                    .clipShape(.rect(cornerRadius: 10))
                    .opacity(viewModel.showButton ? 1.0 : 0.5)
            })
            .disabled(!viewModel.showButton)
        }
        .padding(10)
    }
}

#Preview {
    ContentView()
}
